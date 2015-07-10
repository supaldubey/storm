/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author supal
 *
 */
public class MockSQLiteHelper extends SQLiteOpenHelper {


	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	
	public  java.lang.String getDatabaseName() { return "MOCK"; }
	public  void setWriteAheadLoggingEnabled(boolean enabled) {  }
	public  android.database.sqlite.SQLiteDatabase getWritableDatabase() {  return new SQLiteDatabase(); }
	public  android.database.sqlite.SQLiteDatabase getReadableDatabase() {  return new SQLiteDatabase();}
	public synchronized  void close() {  }
	public  void onConfigure(android.database.sqlite.SQLiteDatabase db) {  }
	public  void onDowngrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {  }
	public  void onOpen(android.database.sqlite.SQLiteDatabase db) {  }


	
}
