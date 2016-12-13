/**
 * 
 */
package in.cubestack.android.lib.storm.criteria;

import org.junit.Assert;
import org.junit.Test;

import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.StormRuntimeException;
import in.cubestack.android.lib.storm.criteria.NullBasedRestriction;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.apps.android.storm.entitites.RestrictionTestEntity;

/**
 * @author Supal Dubey
 *
 */
public class NullRestrictionTest {
	NullBasedRestriction nullRestriction;

	@Test(expected=StormRuntimeException.class)
	public void testFailure_columnName() throws Exception {
		nullRestriction = new NullBasedRestriction("names", true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
	}

	
	@Test
	public void testFailure_columnNameMsg() throws Exception {
		try {
			nullRestriction = new NullBasedRestriction("names", false, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		} catch (StormRuntimeException exception) {
			Assert.assertEquals(exception.getMessage(), "No column found mapped to property names in Entity " + RestrictionTestEntity.class);
		}
	}

	
	@Test
	public void testSuccess_isNull() throws Exception {
		nullRestriction = new NullBasedRestriction("name", true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		Assert.assertEquals(nullRestriction.toSqlString(), EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias() + ".NAME IS NULL ");
		Assert.assertFalse(nullRestriction.valueStored());
		
		Assert.assertNull(nullRestriction.values());
	}
	
	@Test
	public void testSuccess_isNtNull() throws Exception {
		nullRestriction = new NullBasedRestriction("name", false, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		Assert.assertEquals(nullRestriction.toSqlString(), EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias() + ".NAME NOT NULL ");
		Assert.assertFalse(nullRestriction.valueStored());
	
	}

	
	@Test
	public void testSuccess_PagingLimitOffset() throws Exception {
		nullRestriction = new NullBasedRestriction("name", true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		nullRestriction.limit(0, 20);
		
		Assert.assertEquals(nullRestriction.toSqlString(), EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias() + ".NAME IS NULL ");
		Assert.assertFalse(nullRestriction.valueStored());
		
		Assert.assertEquals(nullRestriction.sqlString(Order.orderFor(RestrictionTestEntity.class, new String[] {"id"}, SortOrder.ASC)), EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias() + ".NAME IS NULL  ORDER BY  "+EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias()+".ID ASC  LIMIT 20 OFFSET 0");
	}

	
	@Test
	public void testSuccess_PagingByPage() throws Exception {
		nullRestriction = new NullBasedRestriction("name", true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		nullRestriction.page(1);
		
		Assert.assertEquals(nullRestriction.toSqlString(),EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias() + ".NAME IS NULL ");
		Assert.assertFalse(nullRestriction.valueStored());
		
		Assert.assertEquals(nullRestriction.sqlString(Order.orderFor(RestrictionTestEntity.class, new String[] {"id"}, SortOrder.ASC)), EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias() + ".NAME IS NULL  ORDER BY  "+EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias()+".ID ASC  LIMIT 20 OFFSET 0");
	}

}
