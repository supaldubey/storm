package in.cubestack.apps.android.lib.storm.service;

import in.cubestack.apps.android.lib.storm.core.ColumnMetaData;
import in.cubestack.apps.android.lib.storm.core.TableInformation;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

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
public class Tablegenerator {

    private static final String PRIMARY_KEY_SQLITE = " INTEGER PRIMARY KEY";
    private static final char COMMA = ',';
    private static final char SPACE = ' ';


    public String createSQLTableQuery(TableInformation tableInformation) {
        if (tableInformation.getTableName() == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE " + tableInformation.getTableName().toUpperCase() + " ("
                + tableInformation.getPrimaryKeyData().getColumnName());
        stringBuilder.append(PRIMARY_KEY_SQLITE);
        stringBuilder.append(COMMA);
        for (ColumnMetaData columnMetaData : tableInformation.getColumnMetaDataList()) {
            addColumn(stringBuilder, columnMetaData);
        }
        // Delete last comma
        String query = stringBuilder.substring(0, stringBuilder.length() - 1);
        query = query + ")";
        Log.w(getClass().getName(), "Generated Query " + query);
        return query;
    }

    private void addColumn(StringBuilder stringBuilder, ColumnMetaData columnMetaData) {
        stringBuilder.append(SPACE).append(columnMetaData.getColumnName()).append(SPACE);
        switch (columnMetaData.getFiledTypes()) {
            case TEXT:
                stringBuilder.append("TEXT").append(SPACE);
                break;
            case INTEGER:
                stringBuilder.append("INTEGER").append(SPACE);
                break;
            case REAL:
                stringBuilder.append("REAL").append(SPACE);
                break;
            default:
                stringBuilder.append(columnMetaData.getFiledTypes()).append(SPACE);
        }
        stringBuilder.append(COMMA);
    }

    public List<String> alterSQLTableQuery(TableInformation tableInformation) {
        List<String> statements = new ArrayList<String>(0);

        int columnsToadd = 0;
        String query = null;

        for (ColumnMetaData columnMetaData : tableInformation.getColumnMetaDataList()) {
            if (tableInformation.getTableVersion() <= columnMetaData.getAddedVersion()) {
                StringBuilder builder = new StringBuilder(" ALTER TABLE ");
                builder.append(tableInformation.getTableName()).append(" ADD COLUMN ");
                // This column needs to be added in this version. Go ahead for alter query.
                columnsToadd++;
                addColumn(builder, columnMetaData);
                query = builder.substring(0, builder.length() - 1);
                statements.add(query);
            }
            if (columnsToadd > 0) {
                return statements;
            }
        }
        return null;
    }
}
