package android.content;

import java.util.HashMap;

public final class ContentValues
  extends HashMap<Object, Object>
{
public  ContentValues() { }
public  ContentValues(int size) { }
public  ContentValues(android.content.ContentValues from) { }
public  void putNull(java.lang.String key) { }
public  int size() { return 1;}
public  void remove(java.lang.String key) { }
public  void clear() { }
@java.lang.SuppressWarnings(value={"deprecation"})
public  void writeToParcel(android.os.Parcel parcel, int flags) { }
public static final java.lang.String TAG = "ContentValues";
public static final android.os.Parcelable.Creator<android.content.ContentValues> CREATOR;
static { CREATOR = null; }
}
