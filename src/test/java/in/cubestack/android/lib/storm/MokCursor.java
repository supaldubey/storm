/**
 * 
 */
package in.cubestack.android.lib.storm;

import java.util.List;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

/**
 * @author Supal
 *
 */

public class MokCursor implements Cursor {
	
	private boolean closed = false;
	private List<Object[]> content;
	private int currentIndex = -1;
	
	
	@Override
	public void close() {
		closed =true;
	}

	@Override
	public void copyStringToBuffer(int arg0, CharArrayBuffer arg1) {
		
	}

	@Override
	public void deactivate() {
		
	}

	@Override
	public byte[] getBlob(int arg0) {
		if(content.get(currentIndex)[arg0] == null) {
			return null;
		}
		return content.get(currentIndex)[arg0].toString().getBytes();
	}

	@Override
	public int getColumnCount() {
		return content.get(0).length;
	}

	@Override
	public int getColumnIndex(String arg0) {
		return 0;
	}

	@Override
	public int getColumnIndexOrThrow(String arg0) throws IllegalArgumentException {
		return 0;
	}

	@Override
	public String getColumnName(int arg0) {
		return null;
	}

	@Override
	public String[] getColumnNames() {
		return null;
	}

	@Override
	public int getCount() {
		return content.size();
	}

	@Override
	public double getDouble(int arg0) {
		if(content.get(currentIndex)[arg0] == null) {
			return 0.0;
		}
		return Double.parseDouble(content.get(currentIndex)[arg0].toString());
	}

	@Override
	public Bundle getExtras() {
		return null;
	}

	@Override
	public float getFloat(int arg0) {
		if(content.get(currentIndex)[arg0] == null) {
			return 0.0f;
		}
		return Float.parseFloat(content.get(currentIndex)[arg0].toString());
	}

	@Override
	public int getInt(int arg0) {
		if(content.get(currentIndex)[arg0] == null) {
			return 0;
		}
		return Integer.parseInt(content.get(currentIndex)[arg0].toString());
	}

	@Override
	public long getLong(int arg0) {
		if(content.get(currentIndex)[arg0] == null) {
			return 0;
		}
		return Long.parseLong(content.get(currentIndex)[arg0].toString());
	}

	@Override
	public int getPosition() {
		return currentIndex;
	}

	@Override
	public short getShort(int arg0) {
		if(content.get(currentIndex)[arg0] == null) {
			return 0;
		}
		return Short.parseShort(content.get(currentIndex)[arg0].toString());
	}

	@Override
	public String getString(int arg0) {
		if(content.get(currentIndex)[arg0] == null) {
			return null;
		}
		return content.get(currentIndex)[arg0].toString();
	}

	@Override
	public int getType(int arg0) {
		return 0;
	}

	@Override
	public boolean getWantsAllOnMoveCalls() {
		return false;
	}

	@Override
	public boolean isAfterLast() {
		return currentIndex >= content.size();
	}

	@Override
	public boolean isBeforeFirst() {
		return currentIndex < 0;
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public boolean isFirst() {
		return currentIndex ==0;
	}

	@Override
	public boolean isLast() {
		return currentIndex == content.size();
	}

	@Override
	public boolean isNull(int arg0) {
		return false;
	}

	@Override
	public boolean move(int arg0) {
		return false;
	}

	@Override
	public boolean moveToFirst() {
		currentIndex = 0;
		return true;
	}

	@Override
	public boolean moveToLast() {
		currentIndex = content.size() -1;
		return true;
	}

	@Override
	public boolean moveToNext() {
		currentIndex++;
		if(currentIndex >= content.size()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean moveToPosition(int arg0) {
		currentIndex = arg0;
		return true;
	}

	@Override
	public boolean moveToPrevious() {
		currentIndex--;
		if(currentIndex < 0) {
			return false;
		}
		return true;
	}

	@Override
	public void registerContentObserver(ContentObserver arg0) {
		
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
		
	}

	@Override
	public boolean requery() {
		return false;
	}

	@Override
	public Bundle respond(Bundle arg0) {
		return null;
	}

	@Override
	public void setNotificationUri(ContentResolver arg0, Uri arg1) {
		
	}

	@Override
	public void unregisterContentObserver(ContentObserver arg0) {
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
		
	}

	public void setUp(List<Object[]> contnt) {
		this.content = contnt;
	}
	
}
