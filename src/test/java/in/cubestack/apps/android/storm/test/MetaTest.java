/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.QueryGenerator;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.android.lib.storm.criteria.Projection;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.Restrictions;
import in.cubestack.android.lib.storm.criteria.StormProjection;
import in.cubestack.android.lib.storm.criteria.StormRestrictions;

import org.junit.Test;

/**
 * @author arunk
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
		Projection projection = new StormProjection(EntityMetaDataCache.getMetaData(TestEntity.class));

		projection.add("id");
		projection.add("name");

		projection.sum("price");

		Order order = Order.orderFor(TestEntity.class, new String[] { "id", "name" }, SortOrder.DESC);

		String dsql = new QueryGenerator().rawQuery(EntityMetaDataCache.getMetaData(TestEntity.class), restriction.page(3), null, order);
		System.out.println(dsql);

		System.out.println(order);

		dsql = new QueryGenerator().deleteRawQuery(EntityMetaDataCache.getMetaData(TestEntity.class), restriction);
		System.out.println(dsql);
	}
}
