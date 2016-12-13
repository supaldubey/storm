package android.content;

import java.util.HashMap;
import java.util.Map;

/**
 * Stub
 * @author Supal Dubey
 *
 */
public class ContentValues 
{

	public Map<String, String> content = new HashMap<String, String>();
	public void put(String columnName, String value) {
		content.put(columnName, value);
	}

}
