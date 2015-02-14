/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import java.util.List;

import in.cubestack.apps.android.lib.storm.FetchType;
import in.cubestack.apps.android.lib.storm.FieldType;
import in.cubestack.apps.android.lib.storm.annotation.Column;
import in.cubestack.apps.android.lib.storm.annotation.LifeCycle;
import in.cubestack.apps.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.apps.android.lib.storm.annotation.Relation;
import in.cubestack.apps.android.lib.storm.annotation.Table;

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
	
	@Relation(fetchType=FetchType.EAGER, joinColumn="id", targetEntity = TestChild.class)
	private List<TestChild> childs;
	
	

}
