package in.cubestack.apps.android.storm.entitites;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

@Table(name = "TEST_TAB")
public class RestrictionTestEntity {

	@PrimaryKey
	@Column(name = "ID", type = FieldType.INTEGER)
	private int id;

	@Column(name = "NAME", type = FieldType.TEXT)
	private String name;

	@Column(name = "HOURS", type = FieldType.TEXT)
	private int hours;
	
	@Column(name = "RATE", type = FieldType.REAL)
	private float rate;
	
	@Column(name = "COST", type = FieldType.REAL)
	private double cost;
	
}