/**
 * 
 */
package in.cubestack.android.lib.storm.criteria;

import org.junit.Assert;
import org.junit.Test;

import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.android.lib.storm.core.StormRuntimeException;
import in.cubestack.android.lib.storm.criteria.FunctionType;
import in.cubestack.android.lib.storm.criteria.SQLFunction;
import in.cubestack.android.lib.storm.criteria.SimpleSqlFunction;
import in.cubestack.apps.android.storm.entitites.RestrictionTestEntity;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2014 CubeStack. Version built for Flash Back..
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
public class SQLFunctionsTest {
	
	private SQLFunction function;
	
	@Test(expected=StormRuntimeException.class)
	public void testFailure_columnName() throws Exception {
		function = new SimpleSqlFunction("namze", FunctionType.COUNT, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
	}

	
	@Test
	public void testFailure_columnNameMsg() throws Exception {
		try {
			function = new SimpleSqlFunction("nazme", FunctionType.COUNT, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		} catch (StormRuntimeException exception) {
			Assert.assertEquals(exception.getMessage(), "No column found mapped to property nazme in Entity " + RestrictionTestEntity.class);
		}
	}

	
	@Test
	public void basic() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		function = new SimpleSqlFunction("name", FunctionType.COUNT, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		Assert.assertEquals(function.toSqlString(), "count(" + EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias() + ".NAME ) ");
	}

	

}
