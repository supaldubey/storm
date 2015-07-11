/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import java.util.List;

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
	private int name;
	
	
	@Column(name="PRICE", type=FieldType.REAL)
	private int price;
	
	@Relation(fetchType=FetchType.EAGER, joinColumn="parentId", targetEntity = TestChild.class, cascade={CascadeTypes.PERSIST, CascadeTypes.DELETE})
	private List<TestChild> childs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
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
	
	

}
