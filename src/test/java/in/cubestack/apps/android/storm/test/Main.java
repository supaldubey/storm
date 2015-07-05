/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.QueryGenerator;
import in.cubestack.android.lib.storm.core.TableInformation;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.StormRestrictions;

/**
 * @author Ashok
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws Throwable 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, Throwable {
		Class<?> type = Collection.class;
		TableInformation tableInformation = EntityMetaDataCache.getMetaData(type );
        Restriction restriction = new StormRestrictions(tableInformation).notNull(tableInformation.getPrimaryKeyData().getAlias());
        
        String query = new QueryGenerator().rawQuery(tableInformation, restriction, null);
        System.out.println(query);
	}

}
