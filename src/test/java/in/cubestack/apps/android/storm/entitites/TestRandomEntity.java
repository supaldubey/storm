/**
 * 
 */
package in.cubestack.apps.android.storm.entitites;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * @author sdub14
 *
 */
@Table(name="RANDOM")
public class TestRandomEntity {
	
	@PrimaryKey
	@Column(name="ID", type = FieldType.LONG, addedVersion = 1)
	private long id;

	@Column(name="AMT", type = FieldType.REAL)
	private float amount;
}
