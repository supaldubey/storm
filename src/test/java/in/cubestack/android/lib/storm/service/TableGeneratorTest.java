/**
 * 
 */
package in.cubestack.android.lib.storm.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.util.Log;
import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.apps.android.storm.entitites.TestEntity;
import in.cubestack.apps.android.storm.entitites.TestSetBasedEntity;
import junit.framework.Assert;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2011 Supal Dubey, supal.dubey@gmail.com
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

@RunWith(PowerMockRunner.class)
@PrepareForTest(android.util.Log.class) 
public class TableGeneratorTest {
	
	private Tablegenerator tablegenerator;
	
	@Before
	public void init () {
		tablegenerator = new Tablegenerator();
		PowerMockito.mockStatic(Log.class);
	}
	
	@Test(expected= StormException.class)
	public void testBasic_invalidEntity() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		tablegenerator.createSQLTableQuery(EntityMetaDataCache.getMetaData(String.class));
	}
	
	@Test
	public void testBasic() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String sql = tablegenerator.createSQLTableQuery(EntityMetaDataCache.getMetaData(TestEntity.class));
		
		Assert.assertEquals(sql, "CREATE TABLE TEST_TAB (ID INTEGER PRIMARY KEY, NAME TEXT , RATE REAL , PRICE REAL )");
	}
	
	@Test
	public void testBasic_Set() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String sql = tablegenerator.createSQLTableQuery(EntityMetaDataCache.getMetaData(TestSetBasedEntity.class));
		System.out.println(sql);
		Assert.assertEquals(sql, "CREATE TABLE SETTAB (ID INTEGER PRIMARY KEY, PAR_ID INTEGER , RNK INTEGER )");
	}
	
	@Test
	public void testNoAlters() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		List<String> sql = tablegenerator.alterSQLTableQuery(EntityMetaDataCache.getMetaData(TestEntity.class), 5, 4);

		Assert.assertNull(sql);
	}
	
	
	@Test
	public void testAlter() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		List<String> sqls = tablegenerator.alterSQLTableQuery(EntityMetaDataCache.getMetaData(TestEntity.class), 0, 1);
		System.out.println(sqls);
		
		Assert.assertEquals(sqls.size(), 3);
		Assert.assertEquals(sqls.get(0), " ALTER TABLE TEST_TAB ADD COLUMN  NAME TEXT ");
		Assert.assertEquals(sqls.get(1), " ALTER TABLE TEST_TAB ADD COLUMN  RATE REAL ");
		Assert.assertEquals(sqls.get(2), " ALTER TABLE TEST_TAB ADD COLUMN  PRICE REAL ");
	}

}
