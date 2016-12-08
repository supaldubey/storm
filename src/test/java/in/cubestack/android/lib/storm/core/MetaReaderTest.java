/**
 * 
 */
package in.cubestack.android.lib.storm.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.LifeCycle;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;
import in.cubestack.apps.android.storm.entitites.TestEntity;
import in.cubestack.apps.android.storm.entitites.TestHandler;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2011 Supal Dubey, supal.dubey@gmail.com
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
public class MetaReaderTest {

	private MetaDataReader dataReader;

	@Before
	public void init() {
		dataReader = new MetaDataReader();
	}
	
	@Test(expected=StormException.class)
	public void entityNoAnnotation() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		dataReader.readAnnotations(EntityNoAnnotation.class, new AliasGenerator());
	}
	
	@Test
	public void entityNoAnnotation_ExceptionMsg() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		try {
			dataReader.readAnnotations(EntityNoAnnotation.class, new AliasGenerator());
		}catch (StormException exception) {
			Assert.assertEquals(exception.getMessage(), String.format("Please map the class %s with @Table annotation or remove it from @Database declarations.", EntityNoAnnotation.class.getName()));
		}
	}
	
	@Test(expected=StormException.class)
	public void entityNoPK() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		dataReader.readAnnotations(EntityNoPrimaryKey.class, new AliasGenerator());
	}
	
	@Test
	public void entityNoPK_ExceptionMsg() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		try {
			dataReader.readAnnotations(EntityNoPrimaryKey.class, new AliasGenerator());
		}catch (StormException exception) {
			Assert.assertEquals(exception.getMessage(), String.format("Please update entity %s, to contain atleast one column as Primary Key using @PrimaryKey annotation. Also make sure Primary Key has @Column Annotation", EntityNoPrimaryKey.class.getName()));
		}
	}

	
	@Test(expected=StormException.class)
	public void entityNoName() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		dataReader.readAnnotations(EntityNoName.class, new AliasGenerator());
	}
	
	@Test
	public void entityNoName_ExceptionMsg() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		try {
			dataReader.readAnnotations(EntityNoName.class, new AliasGenerator());
		}catch (StormException exception) {
			Assert.assertEquals(exception.getMessage(), String.format("Please provide table name in %s class, with @Table Annoataion name", EntityNoName.class.getName()));
		}
	}

	
	@Test
	public void entitySuccess() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		TableInformation info = dataReader.readAnnotations(TestEntity.class, new AliasGenerator());
		Assert.assertNotNull(info);
	}
	
	@Test
	public void entitySuccessLifeCycle() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		TableInformation info = dataReader.readAnnotations(EntityPrimaryKeyLifeCycle.class, new AliasGenerator());
		Assert.assertNotNull(info);
	}
	
	@Test
	public void readDb() throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		DatabaseMetaData meta = dataReader.fetchDatabaseMetaData(Database.class);
		Assert.assertNotNull(meta);
	}
	
	
	@in.cubestack.android.lib.storm.annotation.Database(name="MY_DB", tables = {EntityPrimaryKeyLifeCycle.class, EntityPrimaryKey.class})
	class Database {}
	
	@Table(name = "ENTITY_PK")
	class EntityPrimaryKey {
		 @PrimaryKey
		 @Column(name="ID", type = FieldType.INTEGER)
		 private int id;
	 }
	
	@Table(name = "")
	class EntityNoName {
		 @PrimaryKey
		 @Column(name="ID", type = FieldType.INTEGER)
		 private int id;
	 }
	
	
	@Table(name = "ENTITY_PK")
	@LifeCycle(handler=TestHandler.class)
	class EntityPrimaryKeyLifeCycle {
		 @PrimaryKey
		 @Column(name="ID", type = FieldType.INTEGER)
		 private int id;
	 }
	
	
	 class EntityNoAnnotation {
		 @SuppressWarnings(value = { "unused" })
		 private int id;
	 }
	 
	 @Table(name = "ENTITY_PK")
	 class EntityNoPrimaryKey {
		 @SuppressWarnings(value = { "unused" })
		 private int id;
	 }
	

}
