/**
 * 
 */
package in.cubestack.android.lib.storm.criteria;

import in.cubestack.android.lib.storm.core.StormRuntimeException;
import in.cubestack.android.lib.storm.core.TableInformation;

/**
 * @author supal
 *
 */
public abstract class PageableRestriction implements Restriction {

	private static final String OFF_ST = " OFFSET ";

	private static final String LMT = " LIMIT ";

	private static final int PAGE_SIZE = 20;
	
	protected static final String SPACES = "  ";
	
	private TableInformation tableInfo;

	private int offset;
	private int limit;
	
	protected void validate(TableInformation tableInfo, String property) {
		this.setTableInfo(tableInfo);
		if (tableInfo.getColumnName(property) == null) {
			throw new StormRuntimeException(
					"No column found mapped to property " + property + " in Entity " + tableInfo.getMappedClass());
		}
	}

	public Restriction page(int page) {
		if (page < 1) {
			throw new StormRuntimeException("Page Number cannot be less than 1");
		}
		offset = (page - 1) * PAGE_SIZE;
		limit = PAGE_SIZE;
		return this;
	}

	public Restriction limit(int offset, int limit) {
		this.limit = limit;
		this.offset = offset;
		return this;
	}

	public abstract String toSqlString();

	@Override
	public String sqlString(Order order) {
		String sql = toSqlString();

		if (order != null) {
			sql += order.orderSql();
		}

		if (limit > 0 && offset >= 0) {
			sql = new StringBuilder(sql).append(LMT).append(limit).append(OFF_ST).append(offset).toString();
		}

		return sql;
	}

	public TableInformation getTableInformation() {
		return tableInfo;
	}

	protected void setTableInfo(TableInformation tableInfo) {
		this.tableInfo = tableInfo;
	}

}
