/**
 * 
 */
package in.cubestack.android.lib.storm.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.Database;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;
import in.cubestack.android.lib.storm.core.MetaDataReader;
import in.cubestack.android.lib.storm.lifecycle.BasicDatabaseUpdateHandler;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2014-15  CubeStack. Built for performance, scalability and ease to use.
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Context.class, Log.class, SQLiteOpenHelper.class, SQLiteStatement.class, SQLiteDatabase.class })
public class StormDBWrapperTest {
	
	private StormDatabaseWrapper stormWrapper ;
	private List<String> sqls;
	
	private SQLiteDatabase sqliteDb;
	
	@Before
	public void init(){
		PowerMockito.mockStatic(Log.class);
		PowerMockito.mock(Context.class);
		
		stormWrapper = new StormDatabaseWrapper(PowerMockito.mock(Context.class), new MetaDataReader().fetchDatabaseMetaData(TestDatabase.class));
	}
	
	private void prepare() {
		sqliteDb = PowerMockito.mock(SQLiteDatabase.class);
		sqls = new ArrayList<String>();
		PowerMockito.doAnswer(new org.mockito.stubbing.Answer<Void>() {
		    @Override
		    public Void answer(InvocationOnMock invocation) throws Throwable {
		    	String sql = (String) invocation.getArguments()[0];
				sqls.add(sql);
		    	System.out.println("/************************************************************/\nPrinting sql\n" + sql);
		        return null; //does nothing
		    }
		}).when(sqliteDb).execSQL(Matchers.anyString());
	}
	
	@Test
	public void testInit() {
		stormWrapper = new StormDatabaseWrapper();
		Assert.assertNotNull(stormWrapper);
		
		Assert.assertNotNull(stormWrapper);
	}
	
	@Test
	public void testOnCreate() {
		prepare();

		stormWrapper.onCreate(sqliteDb);
		
		Assert.assertNotNull(sqls);
		//We have 5 tables
		Assert.assertEquals(sqls.size(), 5);
	}
	
	
	@Test
	public void testOnUpdate() {
		prepare();
		stormWrapper = new StormDatabaseWrapper(PowerMockito.mock(Context.class), new MetaDataReader().fetchDatabaseMetaData(TestDB.class));

		//Update none probably 
		stormWrapper.onUpgrade(sqliteDb, 2, 3);
		
		Assert.assertNotNull(sqls);
		//We have 5 tables
		Assert.assertEquals(sqls.size(), 2);
		
		Assert.assertArrayEquals(sqls.toArray(new String[2]), new String[] {" ALTER TABLE MYTAB ADD COLUMN  test INTEGER ", "CREATE TABLE NEWTAB (ID INTEGER PRIMARY KEY, test INTEGER )"});
	}
	
	
	@Database(name="DBS", tables = {MyTable.class, NewTable.class}, handler = MyHandler.class, version = 3)
	class TestDB {}
	
	class MyHandler extends BasicDatabaseUpdateHandler {
		@Override
		public void onCreate(SQLiteDatabase db) {
			super.onCreate(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			super.onUpgrade(db, oldVersion, newVersion);
		}
	}
	
	//This table has been added in version 2 of the DB
	@Table(name="MYTAB",version=2)
	class MyTable {
		 @PrimaryKey
		 @Column(name="ID", type = FieldType.INTEGER)
		 private int id;
		 
		 //New column has been added to version 3 of the DB
		 @Column(name="test", type = FieldType.INTEGER, addedVersion = 3)
		 private int test;
		 
	}
	
	//This table has to be created in the DB, as it is added in version 3
	@Table(name="NEWTAB",version=3)
	class NewTable {
		 @PrimaryKey
		 @Column(name="ID", type = FieldType.INTEGER)
		 private int id;
		 
		 //New column has been added to version 3 of the DB
		 @Column(name="test", type = FieldType.INTEGER)
		 private int test;
		 
	}

}
