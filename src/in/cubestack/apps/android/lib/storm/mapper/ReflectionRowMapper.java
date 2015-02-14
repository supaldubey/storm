package in.cubestack.apps.android.lib.storm.mapper;

import in.cubestack.apps.android.lib.storm.FetchType;
import in.cubestack.apps.android.lib.storm.core.ColumnMetaData;
import in.cubestack.apps.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.apps.android.lib.storm.core.ReflectionUtil;
import in.cubestack.apps.android.lib.storm.core.RelationMetaData;
import in.cubestack.apps.android.lib.storm.core.TableInformation;
import in.cubestack.apps.android.lib.storm.fields.FieldStrategyHandler;

import java.util.Collection;

import android.database.Cursor;

/**
 * A simple dao framework for Java based ORM
 * Copyright (c) 2011 Supal Dubey, supal.dubey@gmail.com
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class ReflectionRowMapper<E> implements RowMapper<E> {

    private Class<E> mapperClass;

    public ReflectionRowMapper(Class<E> clazz) {
        mapperClass = clazz;
    }

    @Override
    public E map(Cursor cursor, TableInformation tableInformation, E instance) {
        try {
        	int columnIndex = 1;
        	ColumnMetaData metaData = tableInformation.getPrimaryKeyData();
        	Object primaryKeyVal= null;
        	if(instance == null) {
        		instance = (E) mapperClass.newInstance();
	            
	            primaryKeyVal = FieldStrategyHandler.handlerFor(metaData.getFiledTypes()).getValue(cursor, columnIndex++);
	            
				ReflectionUtil.setField(instance, metaData.getAlias(), primaryKeyVal );
	            for (ColumnMetaData columnMetaData : tableInformation.getColumnMetaDataList()) {
					ReflectionUtil.setField(instance, columnMetaData.getAlias(), FieldStrategyHandler.handlerFor(columnMetaData.getFiledTypes()).getValue(cursor, columnIndex++));
	            }
        	}
            // Start mappping relations. .
            if(!tableInformation.isRelational()) {
            	for(RelationMetaData relationMetaData: tableInformation.getRelations()) {
            		if(relationMetaData.getFetchType() == FetchType.EAGER) {
            			mapRelation(relationMetaData, cursor, instance, columnIndex);
            		}
            	}
            }
            
            if(cursor.moveToNext()) {
            	Object primaryKeyNext = FieldStrategyHandler.handlerFor(metaData.getFiledTypes()).getValue(cursor, columnIndex++);
            	if(primaryKeyNext.equals(primaryKeyVal)) {
            		map(cursor, tableInformation, instance);
            	} else {
            		cursor.moveToPrevious();
            	}
            }
            
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return instance;
    }

	@SuppressWarnings("unchecked")
	private void mapRelation(RelationMetaData relationMetaData, Cursor cursor, E instance, int columnIndex) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		String prop = relationMetaData.getProperty();
		Object entity = relationMetaData.getTargetEntity().newInstance();
		
		TableInformation tableInformation = EntityMetaDataCache.getMetaData(relationMetaData.getTargetEntity());
		
		ColumnMetaData metaData = tableInformation.getPrimaryKeyData();
        ReflectionUtil.setField(entity, metaData.getAlias(), FieldStrategyHandler.handlerFor(metaData.getFiledTypes()).getValue(cursor, columnIndex++));
        for (ColumnMetaData columnMetaData : tableInformation.getColumnMetaDataList()) {
			ReflectionUtil.setField(entity, columnMetaData.getAlias(), FieldStrategyHandler.handlerFor(columnMetaData.getFiledTypes()).getValue(cursor, columnIndex++));
        }
		
		if(ReflectionUtil.getFieldValue(instance, prop) == null) {
			// Instantiate and set
			if(relationMetaData.isCollectionBacked()) {
				ReflectionUtil.setField(instance, prop, relationMetaData.getBackingImplementation().newInstance());
				Collection<Object> xCOl = (Collection<Object>) ReflectionUtil.getFieldValue(instance, prop);
				xCOl.add(entity);
			} else {
				ReflectionUtil.setField(instance, prop, entity);
			}
		}
	}

	public E map(Cursor cursor, TableInformation tableInformation) {
		return map(cursor, tableInformation, null);
	}
}
