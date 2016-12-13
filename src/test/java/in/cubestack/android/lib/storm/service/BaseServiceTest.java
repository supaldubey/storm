/**
 * 
 */
package in.cubestack.android.lib.storm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.android.lib.storm.core.StormRuntimeException;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.android.lib.storm.lifecycle.LifeCycleEnvent;
import in.cubestack.android.lib.storm.lifecycle.LifeCycleHandler;
import in.cubestack.android.lib.storm.task.TaskDispatcher;
import in.cubestack.android.lib.storm.util.Reflections;
import in.cubestack.apps.android.storm.entitites.TestAnother;
import in.cubestack.apps.android.storm.entitites.TestEntity;
import in.cubestack.apps.android.storm.entitites.TestRandomEntity;
import in.cubestack.apps.android.storm.entitites.TestSetBasedEntity;
import org.junit.Assert;

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
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Context.class, Log.class, SQLiteOpenHelper.class, SQLiteStatement.class, SQLiteDatabase.class, TaskDispatcher.class, ContentValues.class, Cursor.class })
public class BaseServiceTest {

	protected static final int MAX_ROWS_SUMMATION = 7;
	
	private BaseService baseService;
	private SQLiteDatabase sqliteDb;
	private Holder holder ;
	private Cursor cursor;
	int totalRows = 5;
	
	private List<String> deletedTables;
	
	private String sql;
	
	@Before
	public void init() throws Exception {
		PowerMockito.mockStatic(Log.class);
		PowerMockito.mock(Context.class);

		PowerMockito.suppress(PowerMockito.constructorsDeclaredIn(StormDatabaseWrapper.class));

		@SuppressWarnings("unchecked")
		Map<String, SQLiteOpenHelper> helpers = (Map<String, SQLiteOpenHelper>) Reflections.getFieldStatic(BaseService.class, "DBHELPERS");
		SQLiteOpenHelper dbWrapper =  PowerMockito.mock(StormDatabaseWrapper.class);
		helpers.put("TEST_DB1", dbWrapper);
		
		sqliteDb = PowerMockito.mock(SQLiteDatabase.class);
		SQLiteStatement sqlStatement = PowerMockito.mock(SQLiteStatement.class);
		
		PowerMockito.when(sqliteDb.compileStatement(Matchers.anyString())).thenReturn(sqlStatement);
		PowerMockito.when(sqlStatement.executeInsert()).thenReturn(4l);
		
		
		PowerMockito.when(dbWrapper.getReadableDatabase()).thenReturn(sqliteDb);
		PowerMockito.when(dbWrapper.getWritableDatabase()).thenReturn(sqliteDb);
		
		baseService = new WrapperBaseService(PowerMockito.mock(Context.class), TestDatabase.class);
		
	}
	
	private void prepareDelete() {
		deletedTables = new ArrayList<String>();
		PowerMockito.when(sqliteDb.delete(Matchers.anyString(), Matchers.anyString(), (String[]) Matchers.any())).then(new Answer<Integer>() {
			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				//Store the  table Name for assertion
				deletedTables.add((String)invocation.getArguments()[0]);
				return 1;
			}
		});
		
		PowerMockito.doAnswer(new org.mockito.stubbing.Answer<Void>() {
		    @Override
		    public Void answer(InvocationOnMock invocation) throws Throwable {
		    	sql = (String) invocation.getArguments()[0];
		        return null; //does nothing
		    }
		}).when(sqliteDb).execSQL(Matchers.anyString(), (Object[]) Matchers.any());
		
	}
	
	private void prepareCursor() {
		cursor = PowerMockito.mock(Cursor.class);		
		Random random = new Random();
		totalRows = 0;
		PowerMockito.when(cursor.moveToNext()).then(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				return totalRows++ < MAX_ROWS_SUMMATION;
			}
		});
		
		PowerMockito.when(cursor.getInt(Matchers.anyInt())).thenReturn(random.nextInt());
		PowerMockito.when(cursor.getLong(Matchers.anyInt())).thenReturn(random.nextLong());
		PowerMockito.when(cursor.getString(Matchers.anyInt())).thenReturn(""+random.nextInt(999));
		PowerMockito.when(cursor.getDouble(Matchers.anyInt())).thenReturn(random.nextDouble());
		PowerMockito.when(cursor.getFloat(Matchers.anyInt())).thenReturn(random.nextFloat());
		PowerMockito.when(cursor.getColumnCount()).thenReturn(3);
		PowerMockito.when(cursor.getCount()).thenReturn(4);
		PowerMockito.when(cursor.moveToFirst()).thenReturn(true);
	}
	
	private void prepareForQuery() {
		prepareCursor();
		PowerMockito.when(sqliteDb.rawQuery(Matchers.anyString(), (String[])Matchers.any())).then(new Answer<Cursor>() {

			@Override
			public Cursor answer(InvocationOnMock invocation) throws Throwable {
				sql = (String) invocation.getArguments()[0];
				
				System.out.println("/************************************************************/\nPrinting sql\n" + sql);
				
				return cursor;
			}
		});
	}
	
	private void prepareForUpdate() {
		holder = new Holder();
		PowerMockito.when(sqliteDb.update(Matchers.anyString(), (ContentValues)Matchers.any(), Matchers.anyString(), (String[])Matchers.any()))
					.thenAnswer(new Answer<Integer>() {
						@Override
						public Integer answer(InvocationOnMock invocation) throws Throwable {
							holder.tableName = invocation.getArguments()[0].toString();
							holder.whereClause = invocation.getArguments()[2].toString();
							holder.content = ((ContentValues) invocation.getArguments()[1]).content;
							holder.whereContent = (String[]) invocation.getArguments()[3];
							return 1;
						}
					});
		
	}

	@Test
	public void testInstance() {
		Assert.assertNotNull(baseService);
	}
	
	@Test
	public void testCreateProjections() throws StormException {
		Assert.assertNotNull(baseService.projectionFor(TestEntity.class));
	}
	
	@Test
	public void testCreateRestrictions() throws StormException {
		Assert.assertNotNull(baseService.restrictionsFor(TestEntity.class));
	}
	
	
	@Test
	public void testSave_OneToMany() throws Exception {
		
		TestEntity entity = new TestEntity();
		entity.setName("Dummy Name");
		entity.setPrice(43);
		entity.setRate(4.3);

		
		TestSetBasedEntity setBasedEntity = new TestSetBasedEntity();
		setBasedEntity.setRank(44);
		
		Set<TestSetBasedEntity> setBasedEntities = new HashSet<TestSetBasedEntity>();
		setBasedEntities.add(setBasedEntity);
		setBasedEntity = new TestSetBasedEntity();
		setBasedEntity.setRank(556);
		setBasedEntities.add(setBasedEntity);
		
		entity.setChils(setBasedEntities);
		
		baseService.save(entity);
		
		Assert.assertEquals(entity.getId(), 4);
		Assert.assertEquals(entity.getChils().size(), 2);
		
		for(TestSetBasedEntity sBasedEntity: entity.getChils()) {
			Assert.assertEquals(sBasedEntity.getParentId(), 4);
			Assert.assertEquals(sBasedEntity.getId(), 4);
		}
	}
	
	
	@Test
	public void testSave_collections () throws Exception {
		TestSetBasedEntity setBasedEntity = new TestSetBasedEntity();
		setBasedEntity.setRank(44);
		
		Set<TestSetBasedEntity> setBasedEntities = new HashSet<TestSetBasedEntity>();
		setBasedEntities.add(setBasedEntity);
		setBasedEntity = new TestSetBasedEntity();
		setBasedEntity.setRank(556);
		setBasedEntities.add(setBasedEntity);
		
		baseService.save(setBasedEntities);
		
		for(TestSetBasedEntity sBasedEntity: setBasedEntities) {
			//Parent Id wont be saved as it is not merge save
			Assert.assertEquals(sBasedEntity.getParentId(), 0);
			Assert.assertEquals(sBasedEntity.getId(), 4);
		}
	}
	
	@Test
	public void testSave_OneToOne() throws Exception {
		
		TestEntity entity = new TestEntity();
		entity.setName("Dummy Name");
		entity.setPrice(43);
		entity.setRate(4.3);
		
		TestRandomEntity randomEntity = new TestRandomEntity();
		randomEntity.setAmount(5.66f);
		
		entity.setEntity(randomEntity);
		
		baseService.save(entity);
		
		Assert.assertEquals(entity.getId(), 4);
		Assert.assertEquals(entity.getEntity().getId(), 4);
		Assert.assertEquals(entity.getEntity().getParentId(), 4l);
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		
		TestEntity entity = new TestEntity();
		entity.setName("Dummy Name");
		entity.setPrice(43);
		entity.setRate(4.3);
		entity.setId(7);
		
		prepareForUpdate();
		
		int count = baseService.update(entity);
		
		Assert.assertEquals(count, 1);
		Assert.assertNotNull(entity);
		Assert.assertEquals(entity.getId(), 7);
		Assert.assertNotNull(holder);
		Assert.assertEquals(holder.tableName, "TEST_TAB");
		Assert.assertEquals(holder.whereClause, "ID = ? ");
		
		Assert.assertArrayEquals(holder.whereContent, new String[] {"7"});
		
		Assert.assertEquals(holder.content.size(), 3);
		
		System.out.println(holder.content);
		
	}
	
	@Test
	public void testUpdate_inCollection() throws Exception {
		
		TestEntity entity = new TestEntity();
		entity.setName("Dummy Name");
		entity.setPrice(43);
		entity.setRate(4.3);
		entity.setId(7);
		
		prepareForUpdate();
		
		int count = baseService.update(new LinkedHashSet<TestEntity>(Arrays.asList(new TestEntity[] {entity})));
		
		Assert.assertEquals(count, 1);
		Assert.assertNotNull(entity);
		Assert.assertEquals(entity.getId(), 7);
		Assert.assertNotNull(holder);
		Assert.assertEquals(holder.tableName, "TEST_TAB");
		Assert.assertEquals(holder.whereClause, "ID = ? ");
		
	}
	
	@Test
	public void testSave_OneToOneMulti() throws Exception {
		
		TestEntity entity = new TestEntity();
		entity.setName("Dummy Name");
		entity.setPrice(43);
		entity.setRate(4.3);
		
		TestRandomEntity randomEntity = new TestRandomEntity();
		randomEntity.setAmount(5.66f);
		
		entity.setEntity(randomEntity);
		
		TestAnother anotherEntity = new TestAnother();
		anotherEntity.setAmount(15.66f);
		
		entity.setAnother(anotherEntity);
		
		baseService.save(entity);
		
		Assert.assertEquals(entity.getId(), 4);
		Assert.assertEquals(entity.getEntity().getId(), 4);
		Assert.assertEquals(entity.getEntity().getParentId(), 4l);
	}
	
	@Test
	public void testFind() throws Exception {
		prepareForQuery();
		
		List<TestEntity> response = baseService.findAll(TestEntity.class, Order.orderFor(TestEntity.class, new String[] {"id"}, SortOrder.DESC));
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testFindAll() throws Exception {
		prepareForQuery();
		
		List<TestEntity> response = baseService.findAll(TestEntity.class);
		Assert.assertNotNull(response);
	}
	
	
	
	
	@Test
	public void testFindOne() throws Exception {
		prepareForQuery();
		
		TestEntity response = baseService.findOne(TestEntity.class, baseService.restrictionsFor(TestEntity.class).equals("id", 2));
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testFind_Order() throws Exception {
		prepareForQuery();
		
		List<TestEntity> response = baseService.find(TestEntity.class, baseService.restrictionsFor(TestEntity.class).equals("id", 2), Order.orderFor(TestEntity.class, new String[] {"name"}, SortOrder.ASC));
		
		Assert.assertNotNull(response);
	}
	
	@Test(expected=StormRuntimeException.class)
	public void testFind_Order_InvalidColumn() throws Exception {
		prepareForQuery();
		
		List<TestEntity> response = baseService.find(TestEntity.class, baseService.restrictionsFor(TestEntity.class).equals("id", 2), Order.orderFor(TestEntity.class, new String[] {"namez"}, SortOrder.ASC));
		
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testCount() throws Exception {
		prepareForQuery();
		
		int count = baseService.count(TestEntity.class);
		Assert.assertTrue(count > 0);
	}
	
	@Test
	public void testProject() throws Exception {
		prepareForQuery();
		
		List<Object> count = baseService.project(TestEntity.class, baseService.restrictionsFor(TestEntity.class).forAll(), baseService.projectionFor(TestEntity.class).sum("price"));
		Assert.assertNotNull(count);
	}
	
	@Test
	public void testTruncate() throws Exception {
		prepareDelete();
		
		//Deleting TestEntity will also trigger deletion for Orphan tables
		int response = baseService.truncateTable(TestEntity.class);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(deletedTables);
		Assert.assertEquals(deletedTables.size(), 5);
	}
	
	
	@Test
	public void testDelte() throws Exception {
		prepareDelete();
		
		TestEntity entity = new TestEntity();
		entity.setName("Dummy Name");
		entity.setPrice(43);
		entity.setRate(4.3);
		TestRandomEntity randomEntity = new TestRandomEntity();
		randomEntity.setAmount(5.66f);
		
		entity.setEntity(randomEntity);
		
		TestAnother anotherEntity = new TestAnother();
		anotherEntity.setAmount(15.66f);
		
		TestSetBasedEntity setBasedEntity = new TestSetBasedEntity();
		setBasedEntity.setRank(44);
		
		Set<TestSetBasedEntity> setBasedEntities = new HashSet<TestSetBasedEntity>();
		setBasedEntities.add(setBasedEntity);
		setBasedEntity = new TestSetBasedEntity();
		setBasedEntity.setRank(556);
		setBasedEntities.add(setBasedEntity);
		
		entity.setChils(setBasedEntities);
		entity.setAnother(anotherEntity);
		
		//Deleting TestEntity will also trigger deletion for Orphan tables
		int response = baseService.delete(entity);
		
		Assert.assertEquals(response, 1);
		Assert.assertNotNull(sql);
	}
	
	
	@Test
	public void testRaw() throws Exception {
		prepareForQuery();
		
		List<Object[]> response = baseService.rawQuery("SELECT * FROM TEST_TAB", null);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(response.size(), MAX_ROWS_SUMMATION);
	}
	
	@Test
	public void testTruncate_NonRelations() throws Exception {
		prepareDelete();
		
		//Deleting TestEntity will also trigger deletion for Orphan tables
		int response = baseService.truncateTable(TestRandomEntity.class);
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(deletedTables);
		Assert.assertEquals(deletedTables.size(), 1);
	}
	
	
	@Test
	public void testFindById() throws Exception {
		prepareForQuery();
		
		TestEntity response = baseService.findById(TestEntity.class, 2);
		
		Assert.assertNotNull(response);
	}
	
	@Test(expected=StormException.class)
	public void testError_CreateProjections() throws StormException {
		Assert.assertNotNull(baseService.projectionFor(String.class));
	}
	
	@Test(expected=StormException.class)
	public void testError_CreateRestrictions() throws StormException {
		Assert.assertNotNull(baseService.restrictionsFor(String.class));
	}
	
	
	class Holder {
		String tableName;
		String whereClause;
		String[] whereContent;
		Map<String, String> content;
	}
	

	
	class WrapperBaseService extends BaseService {

		public WrapperBaseService(Context context, Class<TestDatabase> database) {
			super(context, database);
		}
		
		@Override
		public <E> void dispatchTask(LifeCycleHandler<E> handler, LifeCycleEnvent postSave, E entity, Exception throwable) {
			if(throwable != null) {
				throwable.printStackTrace();
			}
			
			//Make sure we donot have an exception running.
			Assert.assertNull(throwable);
		}
		
	}
}
