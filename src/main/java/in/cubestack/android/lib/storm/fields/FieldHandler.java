package in.cubestack.android.lib.storm.fields;

import java.util.Collection;

import android.database.Cursor;

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
public class FieldHandler {

	private static final String OPENING_BRACE = "(";
	private static final String CLOSING_BRACE = ")";
	protected static final String SPACE = " ";
	protected static final String IS_NULL = " IS NULL";
	public static final String QUESTION_MARK = " ? ";
	private static final char COMMA = ',';

	public Object getValue(Cursor cursor, int columnIndex) {
		return cursor.getString(columnIndex);
	}

	public String buildSqlString(String columnName, String symbol, Object value) {
		StringBuilder sql = new StringBuilder();
		if (value == null) {
			sql.append(columnName).append(IS_NULL);
		} else {
			sql.append(columnName).append(SPACE).append(symbol).append(SPACE);
			if (value instanceof Collection<?>) {
				Collection<?> collection = (Collection<?>) value;

				if (!collection.isEmpty()) {
					sql.append(OPENING_BRACE);
					for (@SuppressWarnings("unused")
					Object obj : collection) {
						sql.append(QUESTION_MARK).append(COMMA);
					}
					sql = new StringBuilder(sql.substring(0, sql.length() - 1));

					sql.append(CLOSING_BRACE);
				}

			} else {
				sql.append(QUESTION_MARK);
			}
		}

		return sql.toString();
	}
}
