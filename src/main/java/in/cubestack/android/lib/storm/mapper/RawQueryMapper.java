/**
 * 
 */
package in.cubestack.android.lib.storm.mapper;

import in.cubestack.android.lib.storm.core.TableInformation;
import android.database.Cursor;

/**
 * @author supal
 *
 */
public class RawQueryMapper implements RowMapper<Object[]> {

	@Override
	public Object[] map(Cursor cursor, TableInformation tableInformation, Object[] instance) {
		return map(cursor, tableInformation);
	}

	@Override
	public Object[] map(Cursor cursor, TableInformation tableInformation) {
		Object[] responseList = new Object[cursor.getColumnCount()];
		for (int columnIndex = 0; columnIndex < cursor.getColumnCount(); columnIndex++) {
			responseList[columnIndex] = cursor.getString(columnIndex);
		}
		return responseList;
	}

}
