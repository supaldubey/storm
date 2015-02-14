/**
 * 
 */
package in.cubestack.apps.android.lib.storm.core;

import in.cubestack.apps.android.lib.storm.FetchType;
import in.cubestack.apps.android.lib.storm.criteria.Projection;
import in.cubestack.apps.android.lib.storm.criteria.Restriction;
import in.cubestack.apps.android.lib.storm.criteria.SQLFunction;


/**
 * A core Android SQLite ORM framrwork build for speed and raw execution.
 * Copyright (c) 2014  CubeStack. Version built for Flash Back..
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class QueryGenerator {

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
	private Class<?> entityClass;
	
	
	public String rawQuery(Restriction restriction, Projection projection) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		return rawQuery(EntityMetaDataCache.getMetaData(entityClass), restriction, projection); 
	}
	
	public String rawQuery(TableInformation information, Restriction restriction, Projection projection) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		StringBuilder sql = new StringBuilder();
		sql.append(SELECT_INIT);
		generateColumnNames(information, sql, projection);
		if(information.getRelations() != null ) {
	    	 for(RelationMetaData relationMetaData: information.getRelations()) {
	    		 if(relationMetaData.getFetchType() == FetchType.EAGER) {
		    		 TableInformation reInformation = EntityMetaDataCache.getMetaData(relationMetaData.getTargetEntity());
		    		 if((projection == null || projection.getColumns().contains(relationMetaData.getProperty()))) {
		    			 generateColumnNames(reInformation, sql, null);
		    		 }
	    		 }
	    	 }
   	 	}
		// Trim last comma
		sql.substring(0, sql.length() -2);
		sql.append(FROM).append(information.getTableName() ).append(SPACE).append(information.getAlias());
		
		for(RelationMetaData relationMetaData: information.getRelations()) {
			if(relationMetaData.getFetchType() == FetchType.EAGER) {
				if((projection == null || projection.getColumns().contains(relationMetaData.getProperty()))) {
					TableInformation reInformation = EntityMetaDataCache.getMetaData(relationMetaData.getTargetEntity());
					sql.append(LEFT_JOIN ).append(reInformation.getTableName()).append(SPACE).append(relationMetaData.getAlias());
					sql.append(ON);
					sql.append(relationMetaData.getAlias()).append(DOT).append(validateClause(relationMetaData.getJoinColumn(), reInformation));
					sql.append(EQUALS);
					sql.append(information.getAlias()).append(DOT).append(information.getPrimaryKeyData().getColumnName());
				}
			}
		}
		
		// Append Restrictions
		sql.append(WHERE);
		sql.append(restriction.toSqlString());
		
		//Append Group by clause in case available :)
		if(projection != null && !projection.getAggregateFunctions().isEmpty()) {
			sql.append(GROUP_BY);
			for(String column: projection.getColumns()) {
				sql.append(column).append(COMMA);
			}
			// Trim last comma
			sql.substring(0, sql.length() -2);
		}
		return sql.toString();
	}
	
	
    private String validateClause(String alias, TableInformation tableInformation) {
        String column = tableInformation.getColumnName(alias);

        if (column == null) {
            // You have come till here, that is nothing has matched. GO AWAY AND FIX THE FUCKING CODE..
            throw new RuntimeException("No mapping found for " + alias + " in entity table " + tableInformation.getTableName());
        }
        return column;
    }
	
    private void generateColumnNames(TableInformation tableInformation, StringBuilder builder, Projection projection) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
    	if(projection ==null || projection.getColumns().contains(tableInformation.getAlias() + "." +tableInformation.getPrimaryKeyData().getAlias())) {
    		builder.append(tableInformation.getAlias()).append(DOT).append(tableInformation.getPrimaryKeyData().getColumnName()).append(COMMA);
    	}
		 for(ColumnMetaData columnMetaData: tableInformation.getColumnMetaDataList()) {
			 if(projection ==null || projection.getColumns().contains(tableInformation.getAlias() + "." +columnMetaData.getAlias())) {
				 builder.append(tableInformation.getAlias()).append(DOT).append(columnMetaData.getColumnName()).append(COMMA);
			 }
		 }
		 
		 if(projection != null) {
			 for(SQLFunction sqlFunction: projection.getAggregateFunctions()) {
				 builder.append(sqlFunction.toSqlString()).append(COMMA);
			 }
			 builder.substring(0, builder.length() -2);
		 }
	    builder.substring(0, builder.length() -2);
    }

}

