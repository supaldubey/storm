/**
 * 
 */
package in.cubestack.android.lib.storm.core;

import in.cubestack.android.lib.storm.FetchType;
import in.cubestack.android.lib.storm.SortOrder;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.android.lib.storm.criteria.Projection;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.SQLFunction;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2014 CubeStack. Version built for Flash Back..
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class QueryGenerator {

	private static final String DELETE = "DELETE ";
	private static final String ORDER_BY = " ORDER BY ";
	private static final String INSERT_INTO = "INSERT INTO ";
	private static final String GROUP_BY = " GROUP BY ";
	private static final String WHERE = " WHERE ";
	private static final String EQUALS = " = ";
	private static final String ON = " ON ";
	private static final String LEFT_JOIN = " LEFT OUTER JOIN ";
	private static final String FROM = " FROM ";
	private static final String SELECT_INIT = "SELECT ";
	private static final char DOT = '.';
	private static final char COMMA = ',';
	private static final char SPACE = ' ';
	private static final char OPEN_BRACES = '(';
	private static final char CLOSE_BRACES = ')';
	private static final String VALUES = " VALUES ( ";
	private static final char QUESTION_MARK = '?';


	public String rawQuery(Class<?> entityClass, Restriction restriction, Projection projection) throws IllegalArgumentException, IllegalAccessException, InstantiationException, StormException {
		return rawQuery(EntityMetaDataCache.getMetaData(entityClass), restriction, projection, null);
	}

	public String deleteRawQuery(TableInformation tableInformation, Restriction restriction) {
		return new StringBuilder(DELETE).append(FROM).append(tableInformation.getTableName()).append(SPACE).append(WHERE)
				.append(cleanAlias(restriction.toSqlString(), tableInformation.getAlias())).toString();
	}

	private String cleanAlias(String sqlString, String alias) {

		return sqlString.replaceAll(alias + ".", "");
	}

	public String rawQuery(TableInformation information, Restriction restriction, Projection projection, Order order) throws IllegalArgumentException,
			IllegalAccessException, InstantiationException, StormException {
		
		if(! restriction.getTableInformation().equals(information)) {
			throw new StormException("Meta information for Table and restrictions do not match. Please review: "
					+ "\n Restriction meta: " + restriction.getTableInformation()
							+ "\n Table information " + information);
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_INIT);
		generateColumnNames(information, sql, projection);
		if (information.getRelations() != null && ! information.getRelations().isEmpty()) {
			for (RelationMetaData relationMetaData : information.getRelations()) {
				if (relationMetaData.getFetchType() == FetchType.EAGER) {
					TableInformation reInformation = EntityMetaDataCache.getMetaData(relationMetaData.getTargetEntity());
					if ((projection == null || projection.getColumns().contains(relationMetaData.getProperty()))) {
						generateColumnNames(reInformation, sql, null);
					}
				}
			}
		}

		// Remove the comma
		sql = new StringBuilder(sql.substring(0, sql.length() - 1));

		sql.append(FROM).append(information.getTableName()).append(SPACE).append(information.getAlias());

		for (RelationMetaData relationMetaData : information.getRelations()) {
			if (relationMetaData.getFetchType() == FetchType.EAGER) {
				if ((projection == null || projection.getColumns().contains(relationMetaData.getProperty()))) {
					TableInformation reInformation = EntityMetaDataCache.getMetaData(relationMetaData.getTargetEntity());
					sql.append(LEFT_JOIN).append(reInformation.getTableName()).append(SPACE).append(relationMetaData.getAlias());
					sql.append(ON);
					sql.append(relationMetaData.getAlias()).append(DOT).append(validateClause(relationMetaData.getJoinColumn(), reInformation));
					sql.append(EQUALS);
					if(isEmpty(relationMetaData.getJoinOnColumn())) {
						sql.append(information.getAlias()).append(DOT).append(information.getPrimaryKeyData().getColumnName());
					} else {
						sql.append(information.getAlias()).append(DOT).append(validateClause(relationMetaData.getJoinOnColumn(), information));
					}
				}
			}
		}

		// Append Restrictions
		sql.append(WHERE);
		sql.append(restriction.sqlString(order));

		// Append Group by clause in case available :)
		if (projection != null && !projection.getColumns().isEmpty()) {
			sql.append(GROUP_BY);
			for (String column : projection.getColumns()) {
				sql.append(column).append(COMMA);
			}
			// Trim last comma
			sql = new StringBuilder(sql.substring(0, sql.length() - 1));
		}
		return sql.toString();
	}

	private boolean isEmpty(String joinOnColumn) {
		return joinOnColumn == null || "".equals(joinOnColumn);
	}

	private String validateClause(String alias, TableInformation tableInformation) {
		String column = tableInformation.getColumnName(alias);

		//Probably impossible, as there are prechecks, but lets still check.
		if (column == null) {
			// You have come till here, that is nothing has matched. GO AWAY AND
			throw new StormRuntimeException("No mapping found for " + alias + " in entity table " + tableInformation.getTableName());
		}
		return column;
	}

	private void generateColumnNames(TableInformation tableInformation, StringBuilder builder, Projection projection) throws IllegalArgumentException,
			IllegalAccessException, InstantiationException {
		if (projection == null || projection.getColumns().contains(tableInformation.getAlias() + "." + tableInformation.getPrimaryKeyData().getAlias())) {
			builder.append(tableInformation.getAlias()).append(DOT).append(tableInformation.getPrimaryKeyData().getColumnName()).append(COMMA);
		}
		for (ColumnMetaData columnMetaData : tableInformation.getColumnMetaDataList()) {
			if (projection == null || projection.getColumns().contains(tableInformation.getAlias() + "." + columnMetaData.getAlias())) {
				builder.append(tableInformation.getAlias()).append(DOT).append(columnMetaData.getColumnName()).append(COMMA);
			}
		}

		if (projection != null) {
			for (SQLFunction sqlFunction : projection.getAggregateFunctions()) {
				builder.append(sqlFunction.toSqlString()).append(COMMA);
			}
		}
	}

	public String insertQuery(TableInformation tableInformation, boolean autoGenerate) throws IllegalArgumentException, IllegalAccessException,
			InstantiationException {
		StringBuilder insert = new StringBuilder(INSERT_INTO).append(tableInformation.getTableName()).append(OPEN_BRACES);

		if ( ! autoGenerate) {
			insert.append(tableInformation.getPrimaryKeyData().getColumnName()).append(COMMA);
		}

		for (ColumnMetaData columnMetaData : tableInformation.getColumnMetaDataList()) {
			insert.append(columnMetaData.getColumnName()).append(COMMA);
		}

		insert = new StringBuilder(insert.substring(0, insert.length() - 1));

		insert.append(CLOSE_BRACES).append(VALUES);

		if (! autoGenerate) {
			insert.append(QUESTION_MARK).append(COMMA);
		}

		for (int columns = 0; columns < tableInformation.getColumnMetaDataList().size(); columns++) {
			insert.append(QUESTION_MARK).append(COMMA);
		}
		insert = new StringBuilder(insert.substring(0, insert.length() - 1));

		// Primary Key
		return insert.append(CLOSE_BRACES).toString();
	}

	public String orderBy(String[] props, TableInformation information, SortOrder order) {
		StringBuilder builder = new StringBuilder(ORDER_BY);
		for (String prop : props) {
			builder.append(SPACE);
			builder.append(information.getAlias()).append(DOT);
			builder.append(information.getColumnName(prop));
			builder.append(COMMA);
		}
		builder = new StringBuilder(builder.substring(0, builder.length() - 1));
		builder.append(SPACE);
		builder.append(order);
		builder.append(SPACE);
		return builder.toString();
	}
}
