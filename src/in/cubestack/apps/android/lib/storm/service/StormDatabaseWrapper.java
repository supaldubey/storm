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
