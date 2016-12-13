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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import in.cubestack.android.lib.storm.CascadeTypes;
import in.cubestack.android.lib.storm.FetchType;
import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.LifeCycle;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Relation;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Test Entity
 * 
 * @author Supal Dubey
 *
 */
@Table(name="TEST_TAB")
@LifeCycle(handler=TestHandler.class)
public class TestEntity {
	
	@PrimaryKey
	@Column(name="ID", type=FieldType.INTEGER)
	private int id;
	
	@Column(name="NAME", type=FieldType.TEXT)
	private String name;
	
	@Column(name="RATE", type=FieldType.DOUBLE)
	private double rate;
	
	
	@Column(name="PRICE", type=FieldType.REAL)
	private int price;
	
	@Relation(fetchType=FetchType.EAGER, joinOnColumn="id", joinColumn="parentId", targetEntity = TestChild.class, cascade={CascadeTypes.PERSIST, CascadeTypes.DELETE})
	private List<TestChild> childs =new ArrayList<TestChild>();

	@Relation(fetchType=FetchType.EAGER, joinOnColumn="id", joinColumn="parentId", targetEntity = TestSetBasedEntity.class, cascade={CascadeTypes.PERSIST, CascadeTypes.DELETE})
	private Set<TestSetBasedEntity> chils;

	
	@Relation(fetchType=FetchType.EAGER,   joinOnColumn="id",joinColumn="parentId", targetEntity = TestRandomEntity.class, cascade={CascadeTypes.PERSIST, CascadeTypes.DELETE})
	private TestRandomEntity entity;

	@Relation(fetchType=FetchType.EAGER,  joinColumn="parentId", targetEntity = TestAnother.class, cascade={CascadeTypes.PERSIST, CascadeTypes.DELETE})
	private TestAnother another;

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getRate() {
		return rate;
	}


	public void setRate(double rate) {
		this.rate = rate;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public List<TestChild> getChilds() {
		return childs;
	}


	public void setChilds(List<TestChild> childs) {
		this.childs = childs;
	}


	public Set<TestSetBasedEntity> getChils() {
		return chils;
	}


	public void setChils(Set<TestSetBasedEntity> chils) {
		this.chils = chils;
	}


	public TestRandomEntity getEntity() {
		return entity;
	}


	public void setEntity(TestRandomEntity entity) {
		this.entity = entity;
	}


	public TestAnother getAnother() {
		return another;
	}


	public void setAnother(TestAnother another) {
		this.another = another;
	}
	
	

	

	
	

}
