package in.cubestack.android.lib.storm.criteria;

import in.cubestack.android.lib.storm.core.TableInformation;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2014-15  CubeStack. Built for performance, scalability and ease to use.
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
public class NullBasedRestriction extends PageableRestriction {

	private String property;
	private boolean isNull;
	
	public NullBasedRestriction(String property, boolean isNull, TableInformation tableInformation) {
		setTableInfo(tableInformation);
		this.property = property;
		this.isNull = isNull;
		
		validate(tableInformation, property);
	}
	
	@Override
	public String toSqlString() {
		
		String column = getTableInformation().getColumnName(property);
		column = getTableInformation().getAlias() + "." + column;
		
		if(isNull) {
			return column +" IS NULL ";
		}
		return column +" NOT NULL ";
	}

	@Override
	public boolean valueStored() {
		return false;
	}

	@Override
	public String[] values() {
		return null;
	}
	
	
	@Override
	public String toString() {
		return "[" + property  +SPACES +"] | IS NULL?: " + isNull;
	}
}