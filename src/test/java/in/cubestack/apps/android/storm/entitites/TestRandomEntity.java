/**
 * 
 */
package in.cubestack.apps.android.storm.entitites;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * @author Supal Dubey
 *
 */
@Table(name="RANDOM")
public class TestRandomEntity {
	
	@PrimaryKey
	@Column(name="ID", type = FieldType.LONG, addedVersion = 1)
	private long id;

	@Column(name="AMT", type = FieldType.REAL)
	private float amount;
	
	@Column(name="PAR_ID", type=FieldType.LONG)
	private long parentId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public long getParentId() {
		return parentId;
	}
	
	
}
