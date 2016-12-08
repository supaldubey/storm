package in.cubestack.android.lib.storm.mapper;

import in.cubestack.android.lib.storm.FetchType;
import in.cubestack.android.lib.storm.core.ColumnMetaData;
import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.RelationMetaData;
import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.android.lib.storm.core.TableInformation;
import in.cubestack.android.lib.storm.fields.FieldStrategyHandler;
import in.cubestack.android.lib.storm.util.Reflections;

import java.util.Collection;

import android.database.Cursor;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2014 CubeStack. Version built for Flash Back..
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class ReflectionRowMapper<E> implements RowMapper<E> {

	private Class<E> mapperClass;

	public ReflectionRowMapper(Class<E> clazz) {
		mapperClass = clazz;
	}

	@Override
	public E map(Cursor cursor, TableInformation tableInformation, E instance) {
		try {
			int columnIndex = 0;
			ColumnMetaData metaData = tableInformation.getPrimaryKeyData();
			Object primaryKeyVal = null;
			if (instance == null) {
				instance = (E) mapperClass.newInstance();

				primaryKeyVal = FieldStrategyHandler.handlerFor(metaData.getFiledTypes()).getValue(cursor, columnIndex++);

				Reflections.setField(instance, metaData.getAlias(), primaryKeyVal);
				for (ColumnMetaData columnMetaData : tableInformation.getColumnMetaDataList()) {
					Reflections.setField(instance, columnMetaData.getAlias(), FieldStrategyHandler.handlerFor(columnMetaData.getFiledTypes()).getValue(cursor, columnIndex++));
				}
			}

			mapRelations(cursor, tableInformation, instance, columnIndex);

		} catch (Exception e) {
			//There is nothing much that can be done, so ignoring and printing stack
			e.printStackTrace();
		}
		return instance;
	}

	private void mapRelations(Cursor cursor, TableInformation tableInformation, E instance, int columnIndex) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		ColumnMetaData metaData = tableInformation.getPrimaryKeyData();

		Object priyamryKeyVal = Reflections.getFieldValue(instance, tableInformation.getPrimaryKeyData().getAlias());
		int initialColumnIndex = columnIndex;
		// Start mappping relations. .
		if (tableInformation.isRelational()) {
			int relationsMapper = 0;
			do {
				Object primaryKeyNext = FieldStrategyHandler.handlerFor(metaData.getFiledTypes()).getValue(cursor, 0);
				if (priyamryKeyVal.equals(primaryKeyNext)) {
					//Reset once Relations are mapped.
					columnIndex = initialColumnIndex;
					relationsMapper ++;
					for (RelationMetaData relationMetaData : tableInformation.getRelations()) {
						if (relationMetaData.getFetchType() == FetchType.EAGER) {
							columnIndex = mapRelation(relationMetaData, cursor, instance, columnIndex);
						}
					}
				} else {
					if(relationsMapper > 0) {
						cursor.moveToPrevious();
					}
					break;
				}
			} while (cursor.moveToNext());
		}
	}

	@SuppressWarnings("unchecked")
	private int mapRelation(RelationMetaData relationMetaData, Cursor cursor, E instance, int columnIndex) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String prop = relationMetaData.getProperty();
		Object entity = relationMetaData.getTargetEntity().newInstance();

		TableInformation tableInformation = EntityMetaDataCache.getMetaData(relationMetaData.getTargetEntity());

		ColumnMetaData primaryKeyMetaData = tableInformation.getPrimaryKeyData();
		Object primaryKey = FieldStrategyHandler.handlerFor(primaryKeyMetaData.getFiledTypes()).getValue(cursor, columnIndex++);
		Reflections.setField(entity, primaryKeyMetaData.getAlias(), primaryKey);

		for (ColumnMetaData columnMetaData : tableInformation.getColumnMetaDataList()) {
			Reflections.setField(entity, columnMetaData.getAlias(), FieldStrategyHandler.handlerFor(columnMetaData.getFiledTypes()).getValue(cursor, columnIndex++));
		}
		
		//No Primary Key, hence never saved to database, do not fetch
		if(primaryKey == null || "0".equals(primaryKey.toString())) {
			return columnIndex;
		}
		
		Object valInstance  = Reflections.getFieldValue(instance, prop);

		if (valInstance == null) {
			// Instantiate and set
			if (relationMetaData.isCollectionBacked()) {
				Collection<Object> xCOl = (Collection<Object>) relationMetaData.getBackingImplementation().newInstance();
				xCOl.add(entity);
				Reflections.setField(instance, prop, xCOl);
			} else {
				Reflections.setField(instance, prop, entity);
			}
		} else {
			if (relationMetaData.isCollectionBacked()) {
				Collection<Object> xCOl = (Collection<Object>) Reflections.getFieldValue(instance, prop);
				if(!exists(valInstance, entity, relationMetaData)) {
					xCOl.add(entity);
				}
			} else {
				if(!exists(valInstance, entity, relationMetaData)) {
					Reflections.setField(instance, prop, entity);
				}
			}
		}
		return columnIndex;
	}
	
	
	@SuppressWarnings("unchecked")
	private boolean exists(Object valInstance, Object entity, RelationMetaData metaData) throws IllegalArgumentException, IllegalAccessException, InstantiationException, SecurityException, NoSuchFieldException, StormException {
		TableInformation information = EntityMetaDataCache.getMetaData(entity.getClass());
		String primaryKeyAlias = information.getPrimaryKeyData().getAlias();
		if(metaData.isCollectionBacked()) {
			for(Object oneIns: (Collection<Object>)valInstance) {
				if(Reflections.getFieldValue(oneIns, primaryKeyAlias).equals(Reflections.getFieldValue(entity, primaryKeyAlias))) {
					return true;
				}
			}
		} else {
			if(Reflections.getFieldValue(valInstance, primaryKeyAlias).equals(Reflections.getFieldValue(entity, primaryKeyAlias))) {
				return true;
			}
		}
		return false;
	}

	public E map(Cursor cursor, TableInformation tableInformation) {
		return map(cursor, tableInformation, null);
	}
}
