/**
 * 
 */
package android.database.sqlite;

import in.cubestack.android.lib.storm.MokCursor;

/**
 * @author supal
 *
 */
public class SQLiteDatabase {
	
	public SQLiteDatabase() {  }
	protected  void finalize() throws java.lang.Throwable {  }
	protected  void onAllReferencesReleased() {  }
	@java.lang.Deprecated()
	public  void setLockingEnabled(boolean lockingEnabled) {  }
	public  void beginTransaction() {  }
	public  void beginTransactionNonExclusive() {  }
	public  void beginTransactionWithListener(android.database.sqlite.SQLiteTransactionListener transactionListener) {  }
	public  void beginTransactionWithListenerNonExclusive(android.database.sqlite.SQLiteTransactionListener transactionListener) {  }
	public  void endTransaction() {  }
	public  void setTransactionSuccessful() {  }
	@java.lang.Deprecated()
	public  int getVersion() {  return 1;}
	public  void setVersion(int version) {  }
	public  void setPageSize(long numBytes) {  }
	@java.lang.Deprecated()
	public  void markTableSyncable(java.lang.String table, java.lang.String deletedTable) {  }
	@java.lang.Deprecated()
	public  void markTableSyncable(java.lang.String table, java.lang.String foreignKey, java.lang.String updateTable) {  }
	public  android.database.Cursor query(boolean distinct, java.lang.String table, java.lang.String[] columns, java.lang.String selection, java.lang.String[] selectionArgs, java.lang.String groupBy, java.lang.String having, java.lang.String orderBy, java.lang.String limit) {  return new MokCursor();}
	public  android.database.Cursor query(boolean distinct, java.lang.String table, java.lang.String[] columns, java.lang.String selection, java.lang.String[] selectionArgs, java.lang.String groupBy, java.lang.String having, java.lang.String orderBy, java.lang.String limit, android.os.CancellationSignal cancellationSignal) {  return new MokCursor();}
	public  android.database.Cursor query(java.lang.String table, java.lang.String[] columns, java.lang.String selection, java.lang.String[] selectionArgs, java.lang.String groupBy, java.lang.String having, java.lang.String orderBy) {  return new MokCursor();}
	public  android.database.Cursor query(java.lang.String table, java.lang.String[] columns, java.lang.String selection, java.lang.String[] selectionArgs, java.lang.String groupBy, java.lang.String having, java.lang.String orderBy, java.lang.String limit) {  return new MokCursor();}
	public  android.database.Cursor rawQuery(java.lang.String sql, java.lang.String[] selectionArgs) {  return new MokCursor();}
	public  android.database.Cursor rawQuery(java.lang.String sql, java.lang.String[] selectionArgs, android.os.CancellationSignal cancellationSignal) { return new MokCursor(); }
	public  long insert(java.lang.String table, java.lang.String nullColumnHack, android.content.ContentValues values) {  return 1;}
	public  int delete(java.lang.String table, java.lang.String whereClause, java.lang.String[] whereArgs) { return 1; }
	public  int update(java.lang.String table, android.content.ContentValues values, java.lang.String whereClause, java.lang.String[] whereArgs) { return 0; }
	public  void execSQL(java.lang.String sql) throws android.database.SQLException {  }
	public  void execSQL(java.lang.String sql, java.lang.Object[] bindArgs) throws android.database.SQLException {  }
	public  boolean isReadOnly() { return false;  }
	public  boolean isOpen() { return true; }
	public static final int CONFLICT_ROLLBACK = 1;
	public static final int CONFLICT_ABORT = 2;
	public static final int CONFLICT_FAIL = 3;
	public static final int CONFLICT_IGNORE = 4;
	public static final int CONFLICT_REPLACE = 5;
	public static final int CONFLICT_NONE = 0;
	public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
	public static final int OPEN_READWRITE = 0;
	public static final int OPEN_READONLY = 1;
	public static final int NO_LOCALIZED_COLLATORS = 16;
	public static final int CREATE_IF_NECESSARY = 268435456;
	public static final int ENABLE_WRITE_AHEAD_LOGGING = 536870912;
	public static final int MAX_SQL_CACHE_SIZE = 100;

}
