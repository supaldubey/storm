/**
 * 
 */
package in.cubestack.android.lib.storm.service;

import java.util.HashMap;
import java.util.Map;

import android.database.sqlite.SQLiteStatement;

/**
 * @author supal
 *
 */
public class StatementCache {

	private Map<Class<?>, SQLiteStatement> STATEMENTS_CACHE = new HashMap<Class<?>, SQLiteStatement>();
	
	public SQLiteStatement fetch(Class<?> clazz) {
		SQLiteStatement liteStatement = null;
		liteStatement = STATEMENTS_CACHE.get(clazz);
		
		return liteStatement;
	}
	
	public void set(Class<?> type, SQLiteStatement liteStatement) {
		STATEMENTS_CACHE.put(type, liteStatement);
	}

	public void clear() {
		STATEMENTS_CACHE.clear();
	}
	
}
