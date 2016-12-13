/**
 * 
 */
package in.cubestack.android.lib.storm.criteria;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.apps.android.storm.entitites.TestEntity;

/**
 * @author sdub14
 *
 */
public class MultiCriteriaTests {
	
	private Restrictions restrictions;
	
	@Before
	public void init() {
		restrictions = StormRestrictions.restrictionsFor(TestEntity.class);
	}
	
	@Test
	public void testAnd () throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		Restriction idRes = restrictions.equals("id", 7);
		Restriction name = restrictions.likeIgnoreCase("name", "SUPAL DUBEY");
		
		Restriction combo = restrictions.and(idRes, name);
		
		Assert.assertNotNull(combo);
		Assert.assertTrue(combo.valueStored());
		
		//Move to lower case
		Assert.assertArrayEquals(combo.values(), new String[] {"7", "supal dubey"});
		
		String sql = "(#ALIAS#.ID =  ?  AND lower(#ALIAS#.NAME) LIKE ? )".replaceAll("#ALIAS#", EntityMetaDataCache.getMetaData(TestEntity.class).getAlias());
		Assert.assertEquals(combo.toSqlString(), sql);
		
	}
	
	
	@Test
	public void testOr() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		Restriction idRes = restrictions.equals("id", 7);
		Restriction name = restrictions.likeIgnoreCase("name", "SUPAL DUBEY");
		
		Restriction combo = restrictions.or(idRes, name);
		
		Assert.assertNotNull(combo);
		Assert.assertTrue(combo.valueStored());
		
		//Move to lower case
		Assert.assertArrayEquals(combo.values(), new String[] {"7", "supal dubey"});
		
		String sql = "(#ALIAS#.ID =  ?  OR lower(#ALIAS#.NAME) LIKE ? )".replaceAll("#ALIAS#", EntityMetaDataCache.getMetaData(TestEntity.class).getAlias());
		Assert.assertEquals(combo.toSqlString(), sql);
		
	}
	
	@Test
	public void testAndOr() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		Restriction idRes = restrictions.equals("id", 7);
		Restriction name = restrictions.likeIgnoreCase("name", "SUPAL DUBEY");
		Restriction combo1 = restrictions.or(idRes, name);
		
		idRes = restrictions.greaterThen("id", 7);
		name = restrictions.notEquals("name", "SUPAL DUBEY");
		Restriction combo2 = restrictions.and(idRes, name);
		
		Restriction combo = restrictions.or(combo1, combo2);
		
		Assert.assertNotNull(combo);
		Assert.assertTrue(combo.valueStored());
		
		//Move to lower case
		Assert.assertArrayEquals(combo.values(), new String[] {"7", "supal dubey", "7", "SUPAL DUBEY"});
		
		String sql = "((#ALIAS#.ID =  ?  OR lower(#ALIAS#.NAME) LIKE ? ) OR (#ALIAS#.ID >  ?  AND #ALIAS#.NAME !=  ? ))".replaceAll("#ALIAS#", EntityMetaDataCache.getMetaData(TestEntity.class).getAlias());
		Assert.assertEquals(combo.toSqlString(), sql);
		
	}

}
