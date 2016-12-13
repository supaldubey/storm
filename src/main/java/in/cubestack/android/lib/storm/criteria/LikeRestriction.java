package in.cubestack.android.lib.storm.criteria;

import in.cubestack.android.lib.storm.core.TableInformation;

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
public class LikeRestriction extends PageableRestriction {

	private static final String LIKE = "LIKE";
	private String property;
	private Object value;
	private boolean ignore;

	public LikeRestriction(String property, Object value, boolean ignoreCase, TableInformation information) {
		this.property = property;
		this.value = value;
		this.ignore = ignoreCase;
		setTableInfo(information);

		validate(information, property);
	}

	@Override
	public String toSqlString() {
		String column = getTableInformation().getColumnName(property);
		if (value == null) {
			return getTableInformation().getAlias() + "." + column + " IS NULL ";
		}
		column = getTableInformation().getAlias() + "." + column;
		if (ignore) {
			return "lower(" + column + ") LIKE ? ";
		} else {
			return column + " LIKE ? ";
		}
	}

	@Override
	public boolean valueStored() {
		return true;
	}

	@Override
	public String[] values() {
		if(value != null && ignore) {
			value = value.toString().toLowerCase();
		}
		return new String[] { value != null ? value.toString() : null };
	}
	
	@Override
	public String toString() {
		return "[" + property + SPACES + LIKE +SPACES + value+"] | Ignore case: " + ignore;
	}

}
