/**
 * 
 */
package in.cubestack.apps.android.lib.storm.service;

import in.cubestack.apps.android.lib.storm.core.DatabaseMetaData;
import in.cubestack.apps.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.apps.android.lib.storm.core.TableInformation;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author arunk
 *
 */
public class StormDatabaseWrapper extends SQLiteOpenHelper {

	private static final String TAG = "StormOrm";
	private DatabaseMetaData metaData;

	public StormDatabaseWrapper(Context context, DatabaseMetaData metaData) {
        super(context, metaData.getName(), null, metaData.getVersion());
        this.metaData = metaData;
    }


	@Override
    public void onCreate(SQLiteDatabase db) {
        Tablegenerator tablegenerator = new Tablegenerator();
        for (Class<?> table : metaData.getTables()) {
            try {
                db.execSQL(tablegenerator.createSQLTableQuery(EntityMetaDataCache.getMetaData(table)));
            } catch (Exception ex) {
                Log.e(TAG, "Error while creating table for class " + table, ex);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Tablegenerator tablegenerator = new Tablegenerator();
        try {
            for (Class<?> table : metaData.getTables()) {
                TableInformation information = EntityMetaDataCache.getMetaData(table);
                if (information.getTableVersion() == metaData.getVersion()) {
                    // Create this table now ..
                    db.execSQL(tablegenerator.createSQLTableQuery(information));
                } else {
                    List<String> sqls = tablegenerator.alterSQLTableQuery(information);
                    if (sqls != null) {
                        for (String sql : sqls) {
                            // Execute them baby
                            Log.w(TAG, " Executing sql " + sql);
                            db.execSQL(sql);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error while creating tale for class ", ex);
        }
    }
}
