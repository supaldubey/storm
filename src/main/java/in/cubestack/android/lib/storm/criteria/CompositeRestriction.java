package in.cubestack.android.lib.storm.criteria;

import java.util.LinkedList;
import java.util.List;

import in.cubestack.android.lib.storm.core.StormRuntimeException;


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
public class CompositeRestriction extends PageableRestriction {
	
	private static final char OPENING_BRACES = '(';
	private static final char CLOSING_BRACES = ')';
	private static final char SPACE = ' ';
	private Restriction left;
	private Restriction right;
	private RestricitionJoin join;

	public CompositeRestriction(Restriction left, Restriction right, RestricitionJoin join) {
		this.left = left;
		this.right = right;
		
		if(! left.getTableInformation().equals(right.getTableInformation())) {
			throw new StormRuntimeException("The meta data information for resrtictions do not match. " + this+ "\n Details "
					+ "Left restriction Meta data: " + left.getTableInformation()+"\nRight Restriction meta data: " + right.getTableInformation());
		}
		setTableInfo(left.getTableInformation());
		this.join = join;
	}
	
	@Override
	public String toSqlString() {
		StringBuilder sql = new StringBuilder();
		sql
			.append(OPENING_BRACES)
			.append(left.toSqlString())
			.append(SPACE)
			.append(join)
			.append(SPACE)
			.append(right.toSqlString())
			.append(CLOSING_BRACES);
		return sql.toString();
	}

	@Override
	public boolean valueStored() {
		return true;
	}

	@Override
	public String[] values() {
		List<String> values = new LinkedList<String>();
		add(left, values);
		add(right, values);
		return values.toArray(new String[values.size()]);
	}
	
	private void add(Restriction cri, List<String> values) {
		if(cri.valueStored()) {
			for(String obj: cri.values()) {
				values.add(obj);
			}
		}
	}
	
	@Override
	public String toString() {
		return left.toString() + SPACES + join + right.toString();
	}
}