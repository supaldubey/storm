/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * @author arunk
 *
 */
@Table(name="CHILD")
public class TestChild {
	
	public TestChild() {
	}
	
	
	public TestChild(long id, String name, long parId) {
		this.id = id;
		this.name =name;
		this.parentId = parId;
	}
	
	
	
	@PrimaryKey
	@Column(name="ID", type=FieldType.INTEGER )
	private long id;

	
	@Column(name="childNam", type=FieldType.TEXT)
	private String name;
	
	
	@Column(name="PAR_ID", type=FieldType.LONG)
	private long parentId;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getParentId() {
		return parentId;
	}


	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	
	
}
