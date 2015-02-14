/**
 *
 */
package in.cubestack.apps.android.lib.storm.core;

import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple dao framework for Java based ORM
 * Copyright (c) 2011 Supal Dubey, supal.dubey@gmail.com
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
public class TableInformation {

	private int tableVersion;
    private String tableName;
    private ColumnMetaData primaryKeyData;
    private List<ColumnMetaData> columnMetaDataList = new LinkedList<ColumnMetaData>();
    private List<RelationMetaData> relations;
    private String alias;
    private LifeCycleHandler<?> handler;
    
    
	public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @param columnMetaDataList the columnMetaDataList to set
     */
    public void setColumnMetaDataList(List<ColumnMetaData> columnMetaDataList) {
        this.columnMetaDataList = columnMetaDataList;
    }

    /**
     * @return the columnMetaDataList
     */
    public List<ColumnMetaData> getColumnMetaDataList() {
        return columnMetaDataList;
    }

    /**
     * @param primaryKeyData the primaryKeyData to set
     */
    public void setPrimaryKeyData(ColumnMetaData primaryKeyData) {
        this.primaryKeyData = primaryKeyData;
    }

    /**
     * @return the primaryKeyData
     */
    public ColumnMetaData getPrimaryKeyData() {
        return primaryKeyData;
    }


    public int getTableVersion() {
        return tableVersion;
    }

    public void setTableVersion(int tableVersion) {
        this.tableVersion = tableVersion;
    }

    @Override
    public String toString() {
        return "[TableInfo {name=" + tableName + "}, {primaryKey=" + primaryKeyData.getAlias() + "/" + primaryKeyData.getColumnName() + "}]";
    }

	public List<RelationMetaData> getRelations() {
		return relations;
	}

	public void setRelations(List<RelationMetaData> relations) {
		this.relations = relations;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public LifeCycleHandler<?> getHandler() {
		return handler;
	}

	public void setHandler(LifeCycleHandler<?> class1) {
		this.handler = class1;
	}

	public boolean isRelational() {
		return relations == null || relations.isEmpty();
	}

	
	public ColumnMetaData getColumnMetaData(String alias) {
		
		if (getPrimaryKeyData().getAlias().equals(alias)) {
            return getPrimaryKeyData();
        }
        for (ColumnMetaData columnMetaData : getColumnMetaDataList()) {
            if (columnMetaData.getAlias().equals(alias)) {
                return columnMetaData;
            }
        }
        return null;
	}
	
	public String getColumnName(String alias) {
		ColumnMetaData columnMetaData = getColumnMetaData(alias);
        if (columnMetaData != null) {
        	return columnMetaData.getColumnName();
        }
        return null;
    }
}
