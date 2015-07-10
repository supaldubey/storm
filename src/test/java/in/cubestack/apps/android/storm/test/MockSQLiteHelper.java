/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author supal
 *
 */
public class MockSQLiteHelper extends SQLiteOpenHelper {


	public MockSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	
	public  java.lang.String getDatabaseName() { return "MOCK"; }
	public  void setWriteAheadLoggingEnabled(boolean enabled) {  }
	public  android.database.sqlite.SQLiteDatabase getWritableDatabase() {  return null; }
	public  android.database.sqlite.SQLiteDatabase getReadableDatabase() {  return null;}
	public synchronized  void close() {  }
	public  void onConfigure(android.database.sqlite.SQLiteDatabase db) {  }
	public  void onDowngrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {  }
	public  void onOpen(android.database.sqlite.SQLiteDatabase db) {  }


	
}
