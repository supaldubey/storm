package android.util;
public final class Log
{
Log() {  }
public static  int v(java.lang.String tag, java.lang.String msg) { return 1; }
public static  int v(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) { System.out.println(msg);   return 1;}
public static  int d(java.lang.String tag, java.lang.String msg) { System.out.println(msg);   return 1;}
public static  int d(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {    System.out.println(msg);   return 1;}
public static  int i(java.lang.String tag, java.lang.String msg) {   System.out.println(msg);   return 1;}
public static  int i(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {  System.out.println(msg);  tr.printStackTrace(); return 1; }
public static  int w(java.lang.String tag, java.lang.String msg) {  System.out.println(msg);   return 1; }
public static  int w(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {   System.out.println(msg);   tr.printStackTrace();return 1;}
public static native  boolean isLoggable(java.lang.String tag, int level);
public static  int w(java.lang.String tag, java.lang.Throwable tr) {  tr.printStackTrace();  return 1; }
public static  int e(java.lang.String tag, java.lang.String msg) {  System.out.println(msg);   return 1; }
public static  int e(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {  System.out.println(msg);   return 1; }
public static  int wtf(java.lang.String tag, java.lang.String msg) {  System.out.println(msg);   return 1; }
public static  int wtf(java.lang.String tag, java.lang.Throwable tr) {  tr.printStackTrace();return 1; }
public static  int wtf(java.lang.String tag, java.lang.String msg, java.lang.Throwable tr) {   System.out.println(msg);   return 1;}
public static  java.lang.String getStackTraceString(java.lang.Throwable tr) { return tr.toString(); }
public static  int println(int priority, java.lang.String tag, java.lang.String msg) {  System.out.println(msg);   return 1; }
public static final int VERBOSE = 2;
public static final int DEBUG = 3;
public static final int INFO = 4;
public static final int WARN = 5;
public static final int ERROR = 6;
public static final int ASSERT = 7;
}
