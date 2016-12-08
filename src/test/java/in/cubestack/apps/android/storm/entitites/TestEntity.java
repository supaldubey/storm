/**
 * 
 */
package in.cubestack.apps.android.storm.entitites;

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
 * @author arunk
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
	
	@Relation(fetchType=FetchType.EAGER, joinColumn="parentId", targetEntity = TestChild.class, cascade={CascadeTypes.PERSIST, CascadeTypes.DELETE})
	private List<TestChild> childs;

	@Relation(fetchType=FetchType.EAGER, joinColumn="parentId", targetEntity = TestSetBasedEntity.class, cascade={CascadeTypes.PERSIST, CascadeTypes.DELETE})
	private Set<TestSetBasedEntity> chils;

	
	@Relation(fetchType=FetchType.EAGER, joinColumn="parentId", targetEntity = TestRandomEntity.class, cascade={CascadeTypes.PERSIST, CascadeTypes.DELETE})
	private TestRandomEntity entity;

	
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
	

}
