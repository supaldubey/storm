/**
 * 
 */
package in.cubestack.android.lib.criteria;

import org.junit.Assert;
import org.junit.Test;

import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.core.AliasGenerator;
import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.StormRuntimeException;
import in.cubestack.android.lib.storm.criteria.LikeRestriction;
import in.cubestack.android.lib.storm.criteria.Order;

/**
 * @author sdub14
 *
 */
public class LikeRestrictionTest {
 
	LikeRestriction likeRestriction;
	AliasGenerator aliasGen = new AliasGenerator();

	@Test(expected=StormRuntimeException.class)
	public void testFailure_columnName() throws Exception {
		likeRestriction = new LikeRestriction("names", "Supal Dubey", false, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
	}

	
	@Test
	public void testFailure_columnNameMsg() throws Exception {
		try {
			likeRestriction = new LikeRestriction("names", "Supal Dubey", false, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		} catch (StormRuntimeException exception) {
			Assert.assertEquals(exception.getMessage(), "No column found mapped to property names in Entity " + RestrictionTestEntity.class);
		}
	}

	
	@Test
	public void testSuccess_caseSensitive() throws Exception {
		likeRestriction = new LikeRestriction("name", "Supal Dubey", false, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		Assert.assertEquals(likeRestriction.toSqlString(), aliasGen.generateAlias(RestrictionTestEntity.class) + ".NAME LIKE ? ");
		Assert.assertEquals(likeRestriction.values().length, 1);
		Assert.assertEquals(likeRestriction.values()[0], "Supal Dubey");
	}
	
	@Test
	public void testSuccess_caseInSensitive() throws Exception {
		likeRestriction = new LikeRestriction("name", "SUPAL DUBEY", true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		Assert.assertEquals(likeRestriction.toSqlString(), "lower("+ aliasGen.generateAlias(RestrictionTestEntity.class) + ".NAME) LIKE ? ");
		Assert.assertEquals(likeRestriction.values().length, 1);
		Assert.assertEquals(likeRestriction.values()[0], "supal dubey");
	
	}

	
	@Test
	public void testSuccess_caseInSensitiveNullVal() throws Exception {
		likeRestriction = new LikeRestriction("name", null, true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		Assert.assertEquals(likeRestriction.toSqlString(), aliasGen.generateAlias(RestrictionTestEntity.class) + ".NAME IS NULL ");
		Assert.assertEquals(likeRestriction.values().length, 1);
		Assert.assertNull(likeRestriction.values()[0]);
	}
	
	
	@Test
	public void testSuccess_PagingLimitOffset() throws Exception {
		likeRestriction = new LikeRestriction("name", null, true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		likeRestriction.limit(0, 20);
		
		Assert.assertEquals(likeRestriction.toSqlString(), aliasGen.generateAlias(RestrictionTestEntity.class) + ".NAME IS NULL ");
		Assert.assertEquals(likeRestriction.values().length, 1);
		Assert.assertNull(likeRestriction.values()[0]);
		
		Assert.assertEquals(likeRestriction.sqlString(Order.orderFor(RestrictionTestEntity.class, new String[] {"id"}, SortOrder.ASC)), aliasGen.generateAlias(RestrictionTestEntity.class) + ".NAME IS NULL  ORDER BY  Retit1.ID ASC  LIMIT 20 OFFSET 0");
	}

	
	@Test(expected=StormRuntimeException.class)
	public void testInvalidPage_PagingByPage() throws Exception {
		likeRestriction = new LikeRestriction("name", null, true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		likeRestriction.page(0);
	}
	
	@Test
	public void testSuccess_PagingByPage() throws Exception {
		likeRestriction = new LikeRestriction("name", null, true, EntityMetaDataCache.getMetaData(RestrictionTestEntity.class));
		likeRestriction.page(1);
		
		Assert.assertEquals(likeRestriction.toSqlString(), aliasGen.generateAlias(RestrictionTestEntity.class) + ".NAME IS NULL ");
		Assert.assertEquals(likeRestriction.values().length, 1);
		Assert.assertNull(likeRestriction.values()[0]);
		
		Assert.assertEquals(likeRestriction.sqlString(Order.orderFor(RestrictionTestEntity.class, new String[] {"id"}, SortOrder.ASC)), aliasGen.generateAlias(RestrictionTestEntity.class) + ".NAME IS NULL  ORDER BY  Retit1.ID ASC  LIMIT 20 OFFSET 0");
	}
}
