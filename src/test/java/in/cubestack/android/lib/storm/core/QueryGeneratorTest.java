/**
 * 
 */
package in.cubestack.android.lib.storm.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Relation;
import in.cubestack.android.lib.storm.annotation.Table;
import in.cubestack.android.lib.storm.criteria.Projection;
import in.cubestack.android.lib.storm.criteria.StormProjection;
import in.cubestack.android.lib.storm.criteria.StormRestrictions;
import in.cubestack.apps.android.storm.entitites.RelationTestEntity;
import in.cubestack.apps.android.storm.entitites.RestrictionTestEntity;
import in.cubestack.apps.android.storm.entitites.TestEntity;
import in.cubestack.apps.android.storm.entitites.TestRandomEntity;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2016  CubeStack. Built for performance, scalability and ease to use.
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class QueryGeneratorTest {
	
	private QueryGenerator generator;
	
	@Before
	public void init() {
		generator = new QueryGenerator();
	}
	
	@Test(expected=StormException.class)
	public void testRawQuery_InvalidTab () throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		generator.rawQuery(String.class, StormRestrictions.restrictionsFor(TestEntity.class).forAll(), null );
	}
	
	@Test(expected=StormException.class)
	public void testRawQuery_NonMatchingMeta () throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		generator.rawQuery(RestrictionTestEntity.class, StormRestrictions.restrictionsFor(TestEntity.class).forAll(), null );
	}
	
	
	@Test
	public void testRawQuery_ForAll () throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String sql = generator.rawQuery(RestrictionTestEntity.class, StormRestrictions.restrictionsFor(RestrictionTestEntity.class).forAll(), null );
		String expected = "SELECT #ALIAS#.ID,#ALIAS#.NAME,#ALIAS#.HOURS,#ALIAS#.RATE,#ALIAS#.COST FROM TEST_TAB #ALIAS# WHERE #ALIAS#.ID NOT NULL ";

		Assert.assertEquals(sql, expected.replaceAll("#ALIAS#", EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias()));
	}

	
	@Test
	public void testRawQuery_ForRestriction () throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String sql = generator.rawQuery(RestrictionTestEntity.class, StormRestrictions.restrictionsFor(RestrictionTestEntity.class).equals("name", "Supal Dubey"), null );
		String expected = "SELECT #ALIAS#.ID,#ALIAS#.NAME,#ALIAS#.HOURS,#ALIAS#.RATE,#ALIAS#.COST FROM TEST_TAB #ALIAS# WHERE #ALIAS#.NAME =  ? ";

		Assert.assertEquals(sql, expected.replaceAll("#ALIAS#", EntityMetaDataCache.getMetaData(RestrictionTestEntity.class).getAlias()));
	}

	
	@Test
	public void testRawQuery_ForAll_NoRel () throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String sql = generator.rawQuery(EntityJoined.class, StormRestrictions.restrictionsFor(EntityJoined.class).equals("id", 2), null );
		String expected = "SELECT #ALIAS#.ID,#ALIAS#.PKID FROM ENTITY_PK #ALIAS# WHERE #ALIAS#.ID =  ? ";

		Assert.assertEquals(sql, expected.replaceAll("#ALIAS#", EntityMetaDataCache.getMetaData(EntityJoined.class).getAlias()));
	}

	
	@Test
	public void testRawQuery_ForAll_Projection() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		
		Projection projection = StormProjection.projectionFor(EntityJoined.class);
		projection.max("pkId").add("id");
		String sql = generator.rawQuery(EntityJoined.class, StormRestrictions.restrictionsFor(EntityJoined.class).forAll(), projection);
		String expected = "SELECT #ALIAS#.ID,count(#ALIAS#.PKID )  FROM ENTITY_PK #ALIAS# WHERE #ALIAS#.ID NOT NULL  GROUP BY #ALIAS#.id";

		Assert.assertEquals(sql, expected.replaceAll("#ALIAS#", EntityMetaDataCache.getMetaData(EntityJoined.class).getAlias()));
	}

	
	@Test
	public void testRawQuery_ForAll_Join_Projection() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		
		Projection projection = StormProjection.projectionFor(Entity.class);
		projection.max("sum").add("id");
		String sql = generator.rawQuery(Entity.class, StormRestrictions.restrictionsFor(Entity.class).forAll(), projection);
		String expected = "SELECT #ALIAS#.ID,count(#ALIAS#.COUNTZ )  FROM ENTITY_MAIN #ALIAS# WHERE #ALIAS#.ID NOT NULL  GROUP BY #ALIAS#.id";

		System.out.println(sql);
		Assert.assertEquals(sql, expected.replaceAll("#ALIAS#", EntityMetaDataCache.getMetaData(Entity.class).getAlias()));
	}
	
	@Test
	public void testRawQuery_ForRelationsAll () throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String sql = generator.rawQuery(RelationTestEntity.class, StormRestrictions.restrictionsFor(RelationTestEntity.class).forAll(), null );
		String expected = "SELECT #PAR_ALIAS#.ID,#CHI_ALIAS#.ID,#CHI_ALIAS#.AMT,#CHI_ALIAS#.PAR_ID FROM TEST_TAB #PAR_ALIAS# LEFT OUTER JOIN RANDOM #CHI_ALIAS# ON #CHI_ALIAS#.PAR_ID = #PAR_ALIAS#.ID WHERE #PAR_ALIAS#.ID NOT NULL ";
		expected = expected.replaceAll("#PAR_ALIAS#", EntityMetaDataCache.getMetaData(RelationTestEntity.class).getAlias());
		expected = expected.replaceAll("#CHI_ALIAS#", EntityMetaDataCache.getMetaData(TestRandomEntity.class).getAlias());

		Assert.assertEquals(sql, expected);
	}
	
	
	@Test
	public void testRawQuery_JoinOn_ForRelationsAll () throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String sql = generator.rawQuery(Entity.class, StormRestrictions.restrictionsFor(Entity.class).forAll(), null );
		String expected = "SELECT #PAR_ALIAS#.ID,#PAR_ALIAS#.COUNTZ,#CHI_ALIAS#.ID,#CHI_ALIAS#.PKID FROM ENTITY_MAIN #PAR_ALIAS# LEFT OUTER JOIN ENTITY_PK #CHI_ALIAS# ON #CHI_ALIAS#.PKID = #PAR_ALIAS#.ID WHERE #PAR_ALIAS#.ID NOT NULL ";
		expected = expected.replaceAll("#PAR_ALIAS#", EntityMetaDataCache.getMetaData(Entity.class).getAlias());
		expected = expected.replaceAll("#CHI_ALIAS#", EntityMetaDataCache.getMetaData(EntityJoined.class).getAlias());

		Assert.assertEquals(sql, expected);
	}

	
	@Test
	public void rawDelete() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		String sql = generator.deleteRawQuery(EntityMetaDataCache.getMetaData(Entity.class), StormRestrictions.restrictionsFor(Entity.class).lessThen("id", 6));
		
		Assert.assertEquals(sql, "DELETE  FROM ENTITY_MAIN  WHERE ID <  ? ");
	}
	
	@Table(name = "ENTITY_PK")
	class EntityJoined {
		 @PrimaryKey
		 @Column(name="ID", type = FieldType.INTEGER)
		 private int id;
		 
		 @Column(name="PKID", type = FieldType.INTEGER)
		 private int pkId;
	 }
	
	@Table(name = "ENTITY_MAIN")
	class Entity {
		 @PrimaryKey
		 @Column(name="ID", type = FieldType.INTEGER)
		 private int id;
		 
		 @Column(name="COUNTZ", type = FieldType.INTEGER)
		 private int sum;
		 
		 @Relation(joinColumn="pkId", targetEntity=EntityJoined.class)
		 EntityJoined entityJoined;
	 }

}
