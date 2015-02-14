/**
 * 
 */
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
public class StormRestrictions implements Restrictions {

	private TableInformation tableInformation;
	
	public StormRestrictions(TableInformation tableInformation) {
		this.tableInformation = tableInformation;
	}
	
	@Override
	public Restriction equals(String property, Object value) {
		return new BasicRestriction(property, value, SQLOperator.EQUALS, tableInformation);
	}

	@Override
	public Restriction notEquals(String property, Object value) {
		return new BasicRestriction(property, value, SQLOperator.NOT_EQUAL, tableInformation);
	}

	@Override
	public Restriction lessThen(String property, Object value) {
		return new BasicRestriction(property, value, SQLOperator.LESS_THEN, tableInformation);
	}

	@Override
	public Restriction greaterThen(String property, Object value) {
		return new BasicRestriction(property, value, SQLOperator.GREATER_THEN, tableInformation);
	}

	@Override
	public Restriction like(String property, String value) {
		return new LikeRestriction(property, value, false, tableInformation);
	}

	@Override
	public Restriction likeIgnoreCase(String property, String value) {
		return new LikeRestriction(property, value, true, tableInformation);
	}
	
	@Override
	public Restriction isNull(String property) {
		return new NullBasedRestriction(property, true, tableInformation);
	}
	
	@Override
	public Restriction notNull(String property) {
		return new NullBasedRestriction(property, false, tableInformation);
	}

	@Override
	public Restriction or(Restriction expression1, Restriction expression2) {
		return new CompositeRestriction(expression1, expression2, RestricitionJoin.OR);
	}

	@Override
	public Restriction and(Restriction expression1, Restriction expression2) {
		return new CompositeRestriction(expression1, expression2, RestricitionJoin.AND);
	}

	@Override
	public Projection projection() {
		return null;
	}

}
