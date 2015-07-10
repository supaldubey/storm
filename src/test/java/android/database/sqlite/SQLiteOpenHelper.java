/**
 * 
 */
package android.database.sqlite;

import android.content.Context;

/**
 * @author supal
 *
 */
public abstract class SQLiteOpenHelper {
	
	public SQLiteOpenHelper () {
		
	}
	
	public SQLiteOpenHelper(Context context, String name, Object object, int version) {
		// TODO Auto-generated constructor stub
	}
	public  java.lang.String getDatabaseName() { return "MOCK"; }
	public  void setWriteAheadLoggingEnabled(boolean enabled) {  }
	public  android.database.sqlite.SQLiteDatabase getWritableDatabase() { return new SQLiteDatabase(); }
	public  android.database.sqlite.SQLiteDatabase getReadableDatabase() { return new SQLiteDatabase(); }
	public synchronized  void close() {  }
	public  void onConfigure(android.database.sqlite.SQLiteDatabase db) {  }
	public abstract  void onCreate(android.database.sqlite.SQLiteDatabase db);
	public abstract  void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion);
	public  void onDowngrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {  }
	public  void onOpen(android.database.sqlite.SQLiteDatabase db) {  }


}
