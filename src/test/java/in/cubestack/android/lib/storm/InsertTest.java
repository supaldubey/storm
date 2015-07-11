/**
 * 
 */
package in.cubestack.android.lib.storm;

import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.QueryGenerator;
import in.cubestack.apps.android.storm.test.TestEntity;

/**
 * @author supal
 *
 */
public class InsertTest {

	/**
	 * @param args
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		System.out.println(new QueryGenerator().insertQuery(EntityMetaDataCache.getMetaData(TestEntity.class)));
	}

}
