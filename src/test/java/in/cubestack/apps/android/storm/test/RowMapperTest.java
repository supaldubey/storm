/**
 * 
 */
package in.cubestack.apps.android.storm.test;

import java.util.ArrayList;
import java.util.List;

import in.cubestack.android.lib.storm.MokCursor;

import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.mapper.ReflectionRowMapper;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Supal
 *
 */
public class RowMapperTest {

	MokCursor cursor = new MokCursor();
	
	ReflectionRowMapper<Collection> reflectionRowMapper;
	
	@Before
	public void set() {
		
		reflectionRowMapper = new ReflectionRowMapper<Collection>(Collection.class);
		
		List<Object[]> contnt = new ArrayList<Object[]>();
		//COLLECTION_ID,COLLECTION_NM,PHOTO_ID,COLLECTN_ID,PHOTO_SVR_ID,PHOTO_URL
		contnt.add(new Object[] {1, "Coll1", 1, 1, 222,"url1"});
		contnt.add(new Object[] {1, "Coll1", 2, 1, 223,"url2"});
		contnt.add(new Object[] {1, "Coll1", 3, 1, 224,"url3"});
		
		contnt.add(new Object[] {2, "Coll2", 1, 2, 222,"url1"});
		contnt.add(new Object[] {2, "Coll2", 4, 2, 225,"url4"});
		contnt.add(new Object[] {3, "Coll3", 5, 3, 226,"url5"});
		
		contnt.add(new Object[] {4, "Coll4", 6, 4, 2277,"url6"});
		
		cursor.setUp(contnt );
	}
	
	@Test
	public void testMappping() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		while(cursor.moveToNext()) {
			Collection collection = reflectionRowMapper.map(cursor, EntityMetaDataCache.getMetaData(Collection.class));
			System.out.println(ToStringBuilder.reflectionToString(collection, ToStringStyle.MULTI_LINE_STYLE));
		}
	}
}
