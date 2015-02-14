/**
 *
 */
package in.cubestack.apps.android.lib.storm.core;


import in.cubestack.apps.android.lib.storm.FieldType;
import in.cubestack.apps.android.lib.storm.annotation.Column;
import in.cubestack.apps.android.lib.storm.annotation.Database;
import in.cubestack.apps.android.lib.storm.annotation.LifeCycle;
import in.cubestack.apps.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.apps.android.lib.storm.annotation.Relation;
import in.cubestack.apps.android.lib.storm.annotation.Table;
import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleHandler;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * A simple dao framework for Java based ORM Copyright (c) 2011 Supal Dubey,
 * supal.dubey@gmail.com
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
public class MetaDataReader {
    private TableInformation tableInformation = new TableInformation();
    
    // Alias must be specific to a mapped Entity

    public TableInformation readAnnotations(Class<?> annotation, AliasGenerator aliasGenerator) throws IllegalArgumentException,
            IllegalAccessException, InstantiationException {
        Table table = (Table) annotation.getAnnotation(Table.class);
        String tableStrName = "";
        if ("".equals(table.schema())) {
            tableStrName = table.name();
        } else {
            tableStrName = table.schema() + "." + table.name();
        }
        tableInformation.setTableName(tableStrName);
        tableInformation.setTableVersion(table.version());
        tableInformation.setAlias(aliasGenerator.generateAlias(annotation));

        if(annotation.isAnnotationPresent(LifeCycle.class)) {
        	Class<?> handlerClass = annotation.getAnnotation(LifeCycle.class).handler();
        	Object handler = handlerClass.newInstance();
        	if( ! (handler instanceof LifeCycleHandler<?>)) {
        		throw new IllegalArgumentException("Class " + handler.getClass() + " is not instance of LifeCycleHandler and cannot be assigned");
        	}
        	tableInformation.setHandler((LifeCycleHandler<?>) handler);
        }
        
        setFields(annotation, aliasGenerator);
        return tableInformation;
    }

    private void setFields(Class<?> annotation, AliasGenerator aliasGenerator) throws IllegalArgumentException, IllegalAccessException {
    	Class<?> clazz = annotation;
        while (clazz.getSuperclass() != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    if (field.isAnnotationPresent(PrimaryKey.class)) {
                        tableInformation.setPrimaryKeyData(getColumnMetaData(field));
                    } else {
                        tableInformation.getColumnMetaDataList().add(getColumnMetaData(field));
                    }
                } else if(field.isAnnotationPresent(Relation.class)) {
                	if(tableInformation.getRelations() == null) {
                		tableInformation.setRelations(new LinkedList<RelationMetaData>());
                	}
                	tableInformation.getRelations().add(new RelationMetaDataReader().fetchRelationMetaData(field, field.getAnnotation(Relation.class), aliasGenerator));
                }
            }
            clazz = clazz.getSuperclass();
        }
        
	}

	public DatabaseMetaData fetchDatabaseMetaData(Class<?> databaseClass) {
        Database database = databaseClass.getAnnotation(Database.class);
        DatabaseMetaData metaData = new DatabaseMetaData();
        metaData.setVersion(database.version());
        metaData.setName(database.name());
        metaData.setTables(database.tables());
        return metaData;
    }	


    private ColumnMetaData getColumnMetaData(Field field) throws IllegalArgumentException, IllegalAccessException {
        ColumnMetaData columnMetaData = new ColumnMetaData();
        Column column = field.getAnnotation(Column.class);
        String alias = field.getName();
        String columnName = column.name();
        boolean parentReferenceKey = column.parentReferenceKey();
        columnMetaData.setParentReferenceKey(parentReferenceKey);
        columnMetaData.setAlias(alias);
        columnMetaData.setColumnName(columnName);
        FieldType fieldType = column.type();
        columnMetaData.setFiledTypes(fieldType);
        columnMetaData.setAddedVersion(column.addedVersion());
        return columnMetaData;
    }
}
