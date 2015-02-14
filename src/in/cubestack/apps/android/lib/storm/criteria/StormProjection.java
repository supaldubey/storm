package in.cubestack.apps.android.lib.storm.criteria;

import in.cubestack.apps.android.lib.storm.core.ColumnMetaData;
import in.cubestack.apps.android.lib.storm.core.TableInformation;

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
public class StormProjection implements Projection {
	
	private TableInformation information;
	private List<String> columns = new LinkedList<>();
	private List<SQLFunction> aggregateFunctions = new LinkedList<>();

	public StormProjection(TableInformation information) {
		this.information = information;
	}

	@Override
	public Projection add(String property) {
		ColumnMetaData columnMetaData = information.getColumnMetaData(property);
		if(columnMetaData != null) {
			columns.add(information.getAlias() + "." + property);
		} else {
			columns.add(property);
		}
		return this;
	}

	@Override
	public Projection sum(String property) {
		aggregateFunctions.add(new SimpleSqlFunction(property, FunctionType.SUM, information));
		return this;
	}


	@Override
	public Projection total(String property) {
		aggregateFunctions.add(new SimpleSqlFunction(property, FunctionType.TOTAL, information));
		return this;
	}

	
	@Override
	public Projection average(String property) {
		aggregateFunctions.add(new SimpleSqlFunction(property, FunctionType.AVERAGE, information));
		return this;
	}
	
	@Override
	public Projection count(String property) {
		aggregateFunctions.add(new SimpleSqlFunction(property, FunctionType.COUNT, information));
		return this;
	}


	@Override
	public Projection max(String property) {
		aggregateFunctions.add(new SimpleSqlFunction(property, FunctionType.COUNT, information));
		return this;
	}


	@Override
	public Projection min(String property) {
		aggregateFunctions.add(new SimpleSqlFunction(property, FunctionType.MIN, information));
		return this;
	}
	
	@Override
	public List<SQLFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}
	
	
	@Override
	public List<String> getColumns() {
		return columns;
	}

}
