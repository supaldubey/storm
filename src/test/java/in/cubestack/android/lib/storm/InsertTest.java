/**
 * 
 */
package in.cubestack.android.lib.storm;

import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.QueryGenerator;
import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.apps.android.storm.entitites.TestEntity;

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
	 * @throws StormException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		System.out.println(new QueryGenerator().insertQuery(EntityMetaDataCache.getMetaData(TestEntity.class), false));
		System.out.println(new QueryGenerator().insertQuery(EntityMetaDataCache.getMetaData(TestEntity.class), true));

	}

	
}
