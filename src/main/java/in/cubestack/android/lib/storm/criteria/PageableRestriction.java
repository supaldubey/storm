/**
 * 
 */
package in.cubestack.android.lib.storm.criteria;

/**
 * @author supal
 *
 */
public abstract class PageableRestriction implements Restriction {

	private static final String OFF_ST = " OFFSET ";

	private static final String LMT = " LIMIT ";

	private static final int PAGE_SIZE = 20;

	private int offset;
	private int limit;

	public Restriction page(int page) {
		if (page < 1) {
			throw new RuntimeException("Page Number cannot be less than 1");
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
	public String sqlString() {
		String sql = toSqlString(); 
		if (limit > 0 && offset >= 0) {
			sql = new StringBuilder(sql)
					.append(LMT)
					.append(limit)
					.append(OFF_ST)
					.append(offset)
					.toString();
		}

		return sql;
	}

}
