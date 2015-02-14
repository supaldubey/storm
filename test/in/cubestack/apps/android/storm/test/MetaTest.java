/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import in.cubestack.apps.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.apps.android.lib.storm.core.QueryGenerator;
import in.cubestack.apps.android.lib.storm.criteria.Projection;
import in.cubestack.apps.android.lib.storm.criteria.Restriction;
import in.cubestack.apps.android.lib.storm.criteria.Restrictions;
import in.cubestack.apps.android.lib.storm.criteria.StormProjection;
import in.cubestack.apps.android.lib.storm.criteria.StormRestrictions;

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
		
		String dsql = new QueryGenerator().rawQuery(EntityMetaDataCache.getMetaData(TestEntity.class), restriction, null);
		System.out.println(dsql);
	}

}
