/**
 * 
 */
package in.cubestack.android.lib.storm.criteria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.android.lib.storm.core.StormRuntimeException;
import in.cubestack.android.lib.storm.criteria.Projection;
import in.cubestack.android.lib.storm.criteria.StormProjection;
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
public class ProjectionTest {

	Projection projection;

	@Before
	public void init() throws StormException {
		projection = StormProjection.projectionFor(RestrictionTestEntity.class);
	}

	@Test
	public void testBasic() {
		projection.total("hours");
		projection.add("hours");
		projection.sum("hours");
		projection.count("hours");
		projection.min("hours");
		projection.max("hours");
		projection.average("hours");

		Assert.assertNotNull(projection);
		Assert.assertEquals(projection.getAggregateFunctions().size(), 6);
		Assert.assertEquals(projection.getColumns().size(), 1);
	}

	@Test(expected = StormException.class)
	public void testBasic_Error() throws StormException {
		projection = StormProjection.projectionFor(String.class);
	}
	
	

	@Test
	public void testBasic_ErrorMsg() {
		try {
			projection.total("hourss");
		} catch (Exception exception) {
			Assert.assertTrue(exception instanceof StormRuntimeException);
			Assert.assertEquals(exception.getMessage(), String.format("No column found mapped to property hourss in Entity class %s", RestrictionTestEntity.class.getName()));
		}
	}

}
