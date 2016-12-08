/**
 * 
 */
package in.cubestack.android.lib.storm.criteria;

import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.core.ColumnMetaData;
import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.QueryGenerator;
import in.cubestack.android.lib.storm.core.StormRuntimeException;
import in.cubestack.android.lib.storm.core.TableInformation;

/**
 * @author supal
 *
 */
public class Order {

	private SortOrder sortOrder;
	private String orderSql;
	private TableInformation information;
	private QueryGenerator queryGenerator = new QueryGenerator();

	public Order(SortOrder order, TableInformation information) {
		this.sortOrder = order;
		this.information = information;
	}

	public static <T> Order orderFor(Class<T> entity, String[] props, SortOrder sortOrder) {
		try {
			TableInformation information = EntityMetaDataCache.getMetaData(entity);
			Order order = new Order(sortOrder, information);
			validate(props, information);
			order.addProperty(props);
			return order;
		} catch (Exception e) {
			throw new StormRuntimeException("Invalid entity, please check your mapppings for " + entity, e);
		}
	}

	private static void validate(String[] props, TableInformation tabInformation) {
		for(String prop: props) {
			ColumnMetaData columnInfo = tabInformation.getColumnMetaData(prop);
			if(columnInfo == null) {
				throw new StormRuntimeException("Could not find column mapped to alias " + prop +". Pl check your mapppings for " + tabInformation.getMappedClass());
			}
		}
	}

	public void addProperty(String[] props) {
		orderSql = queryGenerator.orderBy(props, information, sortOrder);
	}

	public String orderSql() {
		return orderSql;
	}

}
