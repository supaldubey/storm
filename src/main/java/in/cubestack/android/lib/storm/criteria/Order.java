/**
 * 
 */
package in.cubestack.android.lib.storm.criteria;

import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.core.EntityMetaDataCache;
import in.cubestack.android.lib.storm.core.QueryGenerator;
import in.cubestack.android.lib.storm.core.TableInformation;

/**
 * @author supal
 *
 */
public class Order {

	private SortOrder sortOrder;
	private String orderSql;
	private TableInformation information;

	public Order(SortOrder order, TableInformation information) {
		this.sortOrder = order;
		this.information = information;
	}

	public static <T> Order orderFor(Class<T> entity, String[] props, SortOrder sortOrder) {
		try {
			Order order = new Order(sortOrder, EntityMetaDataCache.getMetaData(entity));
			order.addProperty(props);
			return order;
		} catch (Exception e) {
			throw new RuntimeException("Invalid entity, please check your mapppings for " + entity, e);
		}
	}

	public void addProperty(String[] props) {
		orderSql = new QueryGenerator().orderBy(props, information, sortOrder);
	}

	public String orderSql() {
		return orderSql;
	}

}
