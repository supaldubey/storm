/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2016 CubeStack. Built for performance, scalability and ease
 * to use.
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
package in.cubestack.apps.android.storm.entitites;

import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.QueryGenerator;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.android.lib.storm.criteria.Projection;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.Restrictions;
import in.cubestack.android.lib.storm.criteria.StormProjection;
import in.cubestack.android.lib.storm.criteria.StormRestrictions;

import java.util.Arrays;

import org.junit.Test;

/**
 * Random tests
 * 
 * @author Supal Dubey
 *
 */
public class MetaTest {

	/**
	 * @param args
	 * @throws Throwable
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void main() throws IllegalArgumentException, IllegalAccessException, Throwable {
		Restrictions restrictions = new StormRestrictions(EntityMetaDataCache.getMetaData(TestEntity.class));
		Restriction restriction = restrictions.equals("name", "supal");
		restriction = restrictions.and(restriction, restrictions.notNull("price"));
		restriction = restrictions.or(restriction, restrictions.in("name", Arrays.asList("SUpal", "asas")));
		Projection projection = new StormProjection(EntityMetaDataCache.getMetaData(TestEntity.class));

		projection.add("id");
		projection.add("name");

		projection.sum("price");

		System.out.println(restriction.values());

		for (String val : restriction.values()) {
			System.out.println(val);
		}

		Order order = Order.orderFor(TestEntity.class, new String[] { "id", "name" }, SortOrder.DESC);

		String dsql = new QueryGenerator().rawQuery(EntityMetaDataCache.getMetaData(TestEntity.class), restriction.page(3), null, order);
		System.out.println(dsql);

		System.out.println(order);

		dsql = new QueryGenerator().deleteRawQuery(EntityMetaDataCache.getMetaData(TestEntity.class), restriction);
		System.out.println(dsql);
	}
}
