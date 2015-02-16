package in.cubestack.apps.android.lib.storm.service;

import in.cubestack.apps.android.lib.storm.CascadeTypes;
import in.cubestack.apps.android.lib.storm.core.ColumnMetaData;
import in.cubestack.apps.android.lib.storm.core.DatabaseMetaData;
import in.cubestack.apps.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.apps.android.lib.storm.core.QueryGenerator;
import in.cubestack.apps.android.lib.storm.core.RelationMetaData;
import in.cubestack.apps.android.lib.storm.core.StormException;
import in.cubestack.apps.android.lib.storm.core.TableInformation;
import in.cubestack.apps.android.lib.storm.criteria.Projection;
import in.cubestack.apps.android.lib.storm.criteria.Restriction;
import in.cubestack.apps.android.lib.storm.criteria.Restrictions;
import in.cubestack.apps.android.lib.storm.criteria.StormProjection;
import in.cubestack.apps.android.lib.storm.criteria.StormRestrictions;
import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleEnvent;
import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleHandler;
import in.cubestack.apps.android.lib.storm.mapper.RawRowMapper;
import in.cubestack.apps.android.lib.storm.mapper.ReflectionRowMapper;
import in.cubestack.apps.android.lib.storm.mapper.RowMapper;
import in.cubestack.apps.android.lib.storm.task.TaskDispatcher;
import in.cubestack.apps.android.lib.storm.util.Reflections;
import in.cubestack.apps.android.lib.storm.util.StormUtil;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A core Android SQLite ORM framrwork build for speed and raw execution.
 * Copyright (c) 2014  CubeStack. Version built for Flash Back..
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
public class BaseService implements StormService {

    private static final String TAG = BaseService.class.getSimpleName();

    private static SQLiteOpenHelper  dbHelper;
    public static final String CONDITION = " = ? ";

    private BaseService(Context context, DatabaseMetaData databaseMetaData) {
        dbHelper = new StormDatabaseWrapper(context, databaseMetaData);
    }

    @Override
	public <E> Projection projectionFor(Class<E> entity) throws StormException  {
    	try {
			return new StormProjection(EntityMetaDataCache.getMetaData(entity));
		} catch (Exception exception) {
			throw new StormException(exception);
		}
    }
    
    @Override
	public <E> Restrictions restrictionsFor(Class<E> entity) throws StormException  {
    	try {
    		return new StormRestrictions(EntityMetaDataCache.getMetaData(entity));
    	} catch (Exception exception) {
			throw new StormException(exception);
		}
    }
    
    @SuppressWarnings("unchecked")
	public <E> void save(E entity, boolean retain) throws Exception {
        LifeCycleHandler<E> handler = null;
        try {
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(entity.getClass());
            handler = (LifeCycleHandler<E>) tableInformation.getHandler();
            if(handler == null || handler.preSave(entity)) {
            	long id = dbHelper.getWritableDatabase().insert(tableInformation.getTableName(), null, generateContentValues(entity));
            	Reflections.setField(entity, tableInformation.getPrimaryKeyData().getAlias(), id);
            	if(handler != null) {
                	new TaskDispatcher(handler, LifeCycleEnvent.POST_SAVE).execute(entity, null);
                }
            }
        } catch(Throwable throwable) {
        	if(handler != null) {
            	new TaskDispatcher(handler, LifeCycleEnvent.POST_SAVE).execute(entity, throwable);
            } else {
            	throw throwable;
            }
        }
        	finally {
            closeSafe(null, retain);
        }
    }
    
    
	@Override
	public <E> void save(E entity) throws Exception {
    	save(entity, false);
    }
    
    
	@Override
	public <E> void save(List<E> entities) throws Exception {
    	try {
    		dbHelper.getWritableDatabase().beginTransaction();
    		long startTime = System.currentTimeMillis();
    		for(E e: entities){
    			save(e, true);
    		}
    		dbHelper.getWritableDatabase().setTransactionSuccessful();
    		long endTime = System.currentTimeMillis();
    		Log.w(TAG, "Done Bulk Inserts (Time taken: " + (endTime-startTime) + " ms)");
		} catch (SQLException e) {
			Log.w(TAG, "Failed to Bulk Insert (SQLException)", e);
			throw e;
		} catch (Exception e) {
			Log.w(TAG, "Failed to Bulk Insert", e);
		}finally{
			dbHelper.getWritableDatabase().endTransaction();
		}
	}

    @Override
	public <E> int update(E entity) throws Exception {
    	return update(entity, false);
    }
    
    public <E> int update(E entity, boolean retain) throws Exception {
        try {
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(entity.getClass());
            return dbHelper.getWritableDatabase().update(tableInformation.getTableName(), generateContentValues(entity), generateWhereId(tableInformation), generateWhereVal(entity));
        } finally {
            closeSafe(null, false);
        }
    }
    
	@Override
	public <E> void update(List<E> entities) throws Exception {
		long startTime = System.currentTimeMillis();
		dbHelper.getWritableDatabase().beginTransaction();
		for(E e: entities){
			update(e, true);
		}
		dbHelper.getWritableDatabase().setTransactionSuccessful();
		long endTime = System.currentTimeMillis();
		Log.w(TAG, "Done Bulk Updates (Time taken: " + (endTime-startTime) + " ms)");
	}
    

    private <E> String[] generateWhereVal(E entity) {
        try {
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(entity.getClass());
            Object val = Reflections.getFieldValue(entity, tableInformation.getPrimaryKeyData().getAlias());
            if (val != null) {
                return new String[]{val.toString()};
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error Generating where", ex);

        }
        return new String[]{};
    }

    /**
     * Probably this can be customized by calling the below method and passing a criteria.
     * <p/>
     * But wanted to somehow keep both independent. Sounds stupid but lets go with this now.
     *
     * @param id
     * @return
     */
	@Override
	public <E> E findById(Class<E> type, long id) throws Exception {
        E row = null;
        Cursor cursor = null;
        try {
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(type);
            cursor = dbHelper.getReadableDatabase().query(tableInformation.getTableName(), generateColumnNames(tableInformation), generateWhereId(tableInformation), new String[]{"" + id}, null, null, null, null);
            if (cursor.moveToFirst()) {
                row = (E) new ReflectionRowMapper<E>(type).map(cursor, tableInformation);
            }
        } finally {
            closeSafe(cursor, false);
        }
        return row;
    }

    @Override
	public <E> int truncateTable(E entity) throws Exception {
        try {
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(entity.getClass());
            return dbHelper.getWritableDatabase().delete(tableInformation.getTableName(), null, null);
        } finally {
            closeSafe(null, false);
        }
    }
    
    @Override
	@SuppressWarnings("unchecked")
	public <E> int delete(E entity) throws Exception {
    	LifeCycleHandler<E> handler = null;
        try {
        	dbHelper.getWritableDatabase().beginTransaction();
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(entity.getClass());
            handleOrphans(entity, tableInformation);
            handler = (LifeCycleHandler<E>) tableInformation.getHandler();
            if(handler == null || handler.preDelete(entity)) {
            	return dbHelper.getWritableDatabase().delete(tableInformation.getTableName(), generateWhereId(tableInformation), generateWhereVal(entity));
            }
            new TaskDispatcher(handler, LifeCycleEnvent.POST_DELETE).execute(entity, null);
            dbHelper.getWritableDatabase().endTransaction();
        } catch(Throwable throwable) {
        	if(handler != null) {
        		new TaskDispatcher(handler, LifeCycleEnvent.POST_DELETE).execute(entity, throwable);
            } else {
            	throw throwable;
            }
        }
        	finally {
            closeSafe(null, false);
        }
		return 0;
    }
    
    private <E> void handleOrphans(E entity, TableInformation tableInformation) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, Exception {
    	if(tableInformation.isRelational()) {
    		for(RelationMetaData relationMetaData: tableInformation.getRelations()) {
    			boolean isDeletion = false;
    			for(CascadeTypes cascadeTypes: relationMetaData.getCascadeTypes()) {
    				if(CascadeTypes.DELETE.equals(cascadeTypes)) {
    					isDeletion = true;
    					break;
    				}
    			}
    			if(isDeletion) {
    				TableInformation relatedTableInfo = EntityMetaDataCache.getMetaData(relationMetaData.getTargetEntity());
    				ColumnMetaData columnMetaData = tableInformation.getColumnMetaData(relationMetaData.getJoinColumn());
    				dbHelper.getWritableDatabase().delete(relatedTableInfo.getTableName(), columnMetaData.getColumnName() + CONDITION, new String[] {StormUtil.getSafe(Reflections.getFieldValue(entity, relationMetaData.getJoinColumn()))});
    			}
    		}
    	}
    }
    
    
	@Override
	public List<Object> project(Class<?> type, Restriction restriction, Projection projection) throws Exception {
        Cursor cursor = null;
        List<Object> returnList = new ArrayList<Object>();
        RowMapper<List<String>> mapper =  new RawRowMapper();
        try {
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(type);
            
            String query = new QueryGenerator().rawQuery(tableInformation, restriction, projection);
            Log.i("SQUER_QUERY: ", query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, restriction.values());
            while (cursor.moveToNext()) {
                returnList.add(mapper.map(cursor, tableInformation));
            }
        } finally {
            closeSafe(cursor, false);
        }
        return returnList;
    }
    
    @Override
	public <E> List<E> find(Class<E> type, Restriction restriction) throws Exception {
        Cursor cursor = null;
        List<E> returnList = new ArrayList<E>();
        RowMapper<E> mapper =  new ReflectionRowMapper<E>(type);
        try {
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(type);
            
            String query = new QueryGenerator().rawQuery(tableInformation, restriction, null);
            Log.i("SQUER_QUERY: ", query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, restriction.values());
            while (cursor.moveToNext()) {
                returnList.add((E)mapper.map(cursor, tableInformation));
            }
        } finally {
            closeSafe(cursor, false);
        }
        return returnList;
    }
    

    @Override
	public <E> E findOne(Class<E> type, Restriction restriction) throws Exception {
    	Cursor cursor = null;
    	E returnVal  = null;
        RowMapper<E> mapper =  new ReflectionRowMapper<E>(type);
        try {
            TableInformation tableInformation = EntityMetaDataCache.getMetaData(type);
            
            String query = new QueryGenerator().rawQuery(tableInformation, restriction, null);
            Log.i("SQUER_QUERY: ", query);
            cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
            if (cursor.moveToNext()) {
                returnVal = (E)mapper.map(cursor, tableInformation);
            }
        } finally {
            closeSafe(cursor, false);
        }
        return returnVal;
    }
    
    @Override
	public <E> int count(Class<E> type, Restriction restriction) throws Exception {
    	Cursor cursor = null;
    	try {
	    	TableInformation tableInfo = EntityMetaDataCache.getMetaData(type);
	    	Projection projection = new StormProjection(tableInfo);
	    	projection.count(tableInfo.getPrimaryKeyData().getAlias());
	    	QueryGenerator queryGenerator = new QueryGenerator();
	    	String sql = queryGenerator.rawQuery(tableInfo, restriction, projection);
	    	cursor = dbHelper.getReadableDatabase().rawQuery(sql, restriction.values());
	    	
	    	RowMapper<List<String>> mapper = new RawRowMapper();
	    	if(cursor.moveToFirst()) {
	    		List<String> res = mapper.map(cursor, tableInfo);
	    		if(res != null && res.size() > 0) {
	    			String length = res.get(0);
	    			return StormUtil.safeParse(length);
	    		}
	    	}
    	}
    	finally{
    		closeSafe(cursor, false);
    	}
        return 0;
    }

    private <E> String[] generateColumnNames(TableInformation tableInformation) {
        List<String> columns = new ArrayList<String>(tableInformation.getColumnMetaDataList().size() + 1);
        columns.add(tableInformation.getPrimaryKeyData().getColumnName());
        for (ColumnMetaData column : tableInformation.getColumnMetaDataList()) {
            columns.add(column.getColumnName());
        }
        return columns.toArray(new String[columns.size()]);
    }

    private <E> String generateWhereId(TableInformation tableInformation) {
        return tableInformation.getPrimaryKeyData().getColumnName() + CONDITION;
    }
    
    private <E> ContentValues generateContentValues(E entity) throws Exception {
        TableInformation tableInformation = EntityMetaDataCache.getMetaData(entity.getClass());
        ContentValues values = new ContentValues();
        for (ColumnMetaData column : tableInformation.getColumnMetaDataList()) {
            try {
                Object obj = Reflections.getFieldValue(entity, column.getAlias());
                if (obj != null) {
                    values.put(column.getColumnName(), obj.toString());
                } else {
                    values.put(column.getColumnName(), "");
                }
            } catch (Exception ex) {

                Log.e(TAG, "Error Generating Content Vals", ex);
            }
        }
        return values;
    }


    private void closeSafe(Cursor cursor, boolean retainConnection) {
        try {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if(!retainConnection) {
            	dbHelper.close();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error while closing", ex);
        }
    }
    
}
