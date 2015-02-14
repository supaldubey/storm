package in.cubestack.apps.android.lib.storm.criteria;

import in.cubestack.apps.android.lib.storm.FieldType;
import in.cubestack.apps.android.lib.storm.core.ColumnMetaData;
import in.cubestack.apps.android.lib.storm.core.TableInformation;
import in.cubestack.apps.android.lib.storm.fields.FieldStrategyHandler;


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
public class BasicRestriction implements Restriction {
	
	private String property; 
	private Object value;
	private SQLOperator symbol;
	private TableInformation tableInfo;

	public BasicRestriction(String property, Object value, SQLOperator symbol, TableInformation tableInformation) {
		this.property = property;
		this.value = value;
		this.symbol = symbol;
		this.tableInfo = tableInformation;
		
		if(tableInfo.getColumnName(property) == null) {
			throw new RuntimeException("No column found mapped to property " + property);
		}
	}	
	
	@Override
	public String toSqlString() {
		ColumnMetaData columnMetaData = tableInfo.getColumnMetaData(property);
		FieldType type = columnMetaData.getFiledTypes();
		return FieldStrategyHandler.handlerFor(type).buildSqlString(tableInfo.getAlias() + "." +columnMetaData.getColumnName(), symbol.symbol(), value);
	}

	@Override
	public boolean valueStored() {
		return true;
	}

	@Override
	public String[] values() {
		return new String[] {value != null ? value.toString(): null};
	}
}
