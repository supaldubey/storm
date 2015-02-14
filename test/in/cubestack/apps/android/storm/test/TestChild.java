/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import in.cubestack.apps.android.lib.storm.FieldType;
import in.cubestack.apps.android.lib.storm.annotation.Column;
import in.cubestack.apps.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.apps.android.lib.storm.annotation.Table;

/**
 * @author arunk
 *
 */
@Table(name="CHILD")
public class TestChild {
	
	@PrimaryKey
	@Column(name="ID", type=FieldType.INTEGER )
	private long id;

	
}
