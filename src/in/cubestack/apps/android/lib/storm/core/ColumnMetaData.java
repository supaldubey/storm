/**
 *
 */
package in.cubestack.apps.android.lib.storm.core;

import in.cubestack.apps.android.lib.storm.FieldType;

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
public class ColumnMetaData {
    private String columnName;
    private FieldType filedTypes;
    private String alias;
    private int addedVersion;
    private boolean parentReferenceKey;


    public boolean isParentReferenceKey() {
		return parentReferenceKey;
	}

	public void setParentReferenceKey(boolean parentReferenceKey) {
		this.parentReferenceKey = parentReferenceKey;
	}

	public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public FieldType getFiledTypes() {
        return filedTypes;
    }

    public void setFiledTypes(FieldType filedTypes) {
        this.filedTypes = filedTypes;
    }

    public int getAddedVersion() {
        return addedVersion;
    }

    public void setAddedVersion(int addedVersion) {
        this.addedVersion = addedVersion;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }
}
