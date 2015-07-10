/**
 * 
 */
package in.cubestack.apps.android.storm.test;

/**
 * @author supal
 *
 */
@in.cubestack.android.lib.storm.annotation.Database(name="MYDB", tables= {
		TestEntity.class,
		 TestChild.class
})
public class CoreDatabase {

}
