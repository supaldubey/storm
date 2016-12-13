package in.cubestack.android.lib.storm.criteria;

import java.util.Collection;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.core.ColumnMetaData;
import in.cubestack.android.lib.storm.core.TableInformation;
import in.cubestack.android.lib.storm.fields.FieldStrategyHandler;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2014-15 CubeStack. Built for performance, scalability and ease
 * to use.
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
public class BasicRestriction extends PageableRestriction {

	
	protected String property;
	protected Object value;
	protected SQLOperator symbol;

	public BasicRestriction(String property, Object value, SQLOperator symbol, TableInformation tableInformation) {
		this.property = property;
		this.value = value;
		this.symbol = symbol;
		this.setTableInfo(tableInformation);
		
		validate(tableInformation, property);
	}

	@Override
	public String toSqlString() {
		ColumnMetaData columnMetaData = getTableInformation().getColumnMetaData(property);
		FieldType type = columnMetaData.getFiledTypes();
		return FieldStrategyHandler.handlerFor(type).buildSqlString(getTableInformation().getAlias() + "." + columnMetaData.getColumnName(), symbol.symbol(), value);
	} 

	@Override
	public boolean valueStored() {
		return true;
	}

	@Override
	public String[] values() {
		String[] values = null;

		if (value == null) {
			return null;
		} else {
			if (value instanceof Collection<?>) {
				values = new String[((Collection<?>) value).size()];
				int index = 0;
				for (Object obj : (Collection<?>) value) {
					values[index++] = obj != null ? obj.toString() : "";
				}
				return values;
			} else {
				return new String[] { value.toString() };
			}
		}
	}
	
	@Override
	public String toString() {
		return "[" + property + SPACES +symbol +SPACES + value+"]";
	}

}
