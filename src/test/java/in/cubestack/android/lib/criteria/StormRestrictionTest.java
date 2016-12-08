/**
 * 
 */
package in.cubestack.android.lib.criteria;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.core.AliasGenerator;
import in.cubestack.android.lib.storm.core.StormRuntimeException;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.Restrictions;
import in.cubestack.android.lib.storm.criteria.StormRestrictions;

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
public class StormRestrictionTest {

	private Restrictions restrictions;
	private AliasGenerator aliasGenerator;
	
	@Before
	public void init() {
		aliasGenerator = new AliasGenerator();
	}

	@Test(expected = StormRuntimeException.class)
	public void testInvalidClass() {
		restrictions = StormRestrictions.restrictionsFor(String.class);
	}

	public void testInvalidClassExpMsg() {
		try {
			restrictions = StormRestrictions.restrictionsFor(String.class);
		} catch (Exception ex) {
			Assert.assertTrue(ex instanceof StormRuntimeException);
			Assert.assertEquals(ex.getMessage(), "Invalid entity, please check your mapppings for " + String.class.getName());
		}
	}

	
	@Test(expected=StormRuntimeException.class)
	public void testBasic_OrderFail () {
		restrictions = StormRestrictions.restrictionsFor(RestrictionTestEntity.class);
		Assert.assertNotNull(restrictions);
		Restriction res = restrictions.equals("name", "Supal Dubey");
		
		Order order = Order.orderFor(RestrictionTestEntity.class, new String[] {"dsds"}, SortOrder.DESC);
		res.sqlString(order);
	}

	
	@Test
	public void testBasic () {
		restrictions = StormRestrictions.restrictionsFor(RestrictionTestEntity.class);
		
		Assert.assertNotNull(restrictions);
		
		Restriction res = restrictions.equals("name", "Supal Dubey");
		Assert.assertEquals(res.toSqlString(), aliasGenerator.generateAlias(RestrictionTestEntity.class) + ".NAME =  ? ");
		
		Assert.assertEquals(res.values().length, 1);
		Assert.assertEquals(res.values(), new String[] {"Supal Dubey"});
	}
	
	@Test
	public void testComposite() {
		restrictions = StormRestrictions.restrictionsFor(RestrictionTestEntity.class);
		
		Assert.assertNotNull(restrictions);
		
		Restriction resName1 = restrictions.equals("name", "Supal Dubey");
		Restriction resName2 = restrictions.equals("name", "Supal");
		
		Restriction composite = restrictions.or(resName1, resName2);
		
		Assert.assertEquals(composite.toSqlString(), "(" +aliasGenerator.generateAlias(RestrictionTestEntity.class)  +".NAME =  ?  OR " + aliasGenerator.generateAlias(RestrictionTestEntity.class) +".NAME =  ? )");
		
		Assert.assertEquals(composite.values().length, 2);
		Assert.assertEquals(composite.values(), new String[] {"Supal Dubey", "Supal"});
	}

	
	@Test
	public void testIn() {
		restrictions = StormRestrictions.restrictionsFor(RestrictionTestEntity.class);
		
		Assert.assertNotNull(restrictions);
		
		Restriction res = restrictions.in("name", Arrays.asList(new String[] {"Supal Dubey", "Supal", "Name"}));
		Assert.assertEquals(res.toSqlString(), aliasGenerator.generateAlias(RestrictionTestEntity.class) + ".NAME  IN  ( ? , ? , ? )");
		
		Assert.assertEquals(res.values().length, 3);
		Assert.assertEquals(res.values(), new String[] {"Supal Dubey", "Supal", "Name"});
	}

	
	@Test
	public void testCompositeMultiple() {
		restrictions = StormRestrictions.restrictionsFor(RestrictionTestEntity.class);
		
		Assert.assertNotNull(restrictions);
		
		Restriction resName1 = restrictions.equals("name", "Supal Dubey");
		Restriction res2 = restrictions.lessThen("rate", "3.2");
		Restriction res3 = restrictions.greaterThen("hours", "5");
		Restriction res4 = restrictions.notEquals("cost", "500");
		Restriction res5 = restrictions.notNull("name");
		
		Restriction composite1 = restrictions.or(resName1, res2);
		Restriction composite2 = restrictions.or(res3, res4);
		
		Restriction composite  = restrictions.or(res5, restrictions.and(composite2, composite1));
		
		String expectedsql = "(#ALIAS#.NAME NOT NULL  OR ((#ALIAS#.HOURS >  ?  OR #ALIAS#.COST !=  ? ) AND (#ALIAS#.NAME =  ?  OR #ALIAS#.RATE <  ? )))";
		expectedsql = expectedsql.replaceAll("#ALIAS#", aliasGenerator.generateAlias(RestrictionTestEntity.class));
		
		Assert.assertEquals(composite.toSqlString(), expectedsql);
		
		Assert.assertEquals(composite.values().length, 4);
		Assert.assertEquals(composite.values(), new String[] { "5", "500", "Supal Dubey", "3.2"});
	
	}
	

	
	@Test
	public void testCompositeMultiple2() {
		restrictions = StormRestrictions.restrictionsFor(RestrictionTestEntity.class);
		
		Assert.assertNotNull(restrictions);
		
		Restriction resName1 = restrictions.like("name", "Supal Dubey");
		Restriction res2 = restrictions.likeIgnoreCase("name", "SUPAL");
		Restriction res5 = restrictions.isNull("name");
		Restriction res = restrictions.in("name", Arrays.asList(new String[] {"Supal Dubey", "Supal", "Name"}));
		
		Restriction composite1 = restrictions.or(resName1, res2);
		Restriction composite2  = restrictions.or(res5, res);
		Restriction composite  = restrictions.and(composite2, composite1);
		
		String expectedsql = "((#ALIAS#.NAME IS NULL  OR #ALIAS#.NAME  IN  ( ? , ? , ? )) AND (#ALIAS#.NAME LIKE ?  OR lower(#ALIAS#.NAME) LIKE ? ))";
		expectedsql = expectedsql.replaceAll("#ALIAS#", aliasGenerator.generateAlias(RestrictionTestEntity.class));
		
		Assert.assertEquals(composite.toSqlString(), expectedsql);
		
		Assert.assertEquals(composite.values().length, 5);
		Assert.assertEquals(composite.values(), new String[] { "Supal Dubey", "Supal", "Name", "Supal Dubey",  "supal"});
	
	}
	
	@Test
	public void testForAll () {
		restrictions = StormRestrictions.restrictionsFor(RestrictionTestEntity.class);
		
		Assert.assertNotNull(restrictions);
		
		Restriction res = restrictions.forAll();
		Assert.assertEquals(res.toSqlString(), aliasGenerator.generateAlias(RestrictionTestEntity.class) + ".ID NOT NULL ");
		
		Assert.assertFalse(res.valueStored());
	}

}
