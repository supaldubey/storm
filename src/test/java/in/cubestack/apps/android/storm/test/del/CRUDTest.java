/**
 * 
 */
package in.cubestack.apps.android.storm.test.del;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import in.cubestack.android.lib.storm.core.MetaDataReader;
import in.cubestack.android.lib.storm.service.BaseService;
import in.cubestack.android.lib.storm.service.StormService;
import in.cubestack.apps.android.storm.test.CoreDatabase;
import in.cubestack.apps.android.storm.test.MockSQLiteHelper;
import in.cubestack.apps.android.storm.test.TestChild;
import in.cubestack.apps.android.storm.test.TestEntity;

/**
 * @author supal
 *
 */
public class CRUDTest {

	private StormService stormService;
	private TestEntity entity;

	@Before
	public void set() {
		stormService = new BaseService(null, new MetaDataReader().fetchDatabaseMetaData(CoreDatabase.class), new MockSQLiteHelper());
		entity = new TestEntity();
		entity.setId(1);
		entity.setName(2332);
		entity.setPrice(12112);

		List<TestChild> childs = new ArrayList<TestChild>();

		childs.add(genChild(1, 1, "supal"));
		childs.add(genChild(2, 1, "supal"));
		childs.add(genChild(3, 1, "supal"));

		entity.setChilds(childs);
	}

	private TestChild genChild(int i, int j, String string) {
		return new TestChild(i, string, j);
	}

	@Test
	public void del() throws Exception {
		stormService.delete(entity);
	}
	
	@Test
	public void save() throws Exception {
		stormService.save(entity);
	}
}
