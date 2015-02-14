package in.cubestack.apps.android.lib.storm.criteria;

import in.cubestack.apps.android.lib.storm.core.TableInformation;

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
public class NullBasedRestriction implements Restriction {

	private TableInformation tableInformation;
	private String property;
	private boolean isNull;
	
	public NullBasedRestriction(String property, boolean isNull, TableInformation tableInformation) {
		this.tableInformation= tableInformation;
		this.property = property;
		this.isNull = isNull;
	}
	
	@Override
	public String toSqlString() {
		String column = tableInformation.getColumnName(property);
		if(column == null) {
			throw new RuntimeException(" No Column mapped for property "  + property);
		}
		column = tableInformation.getAlias() + "." + column;
		
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
}