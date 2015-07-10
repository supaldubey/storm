package android.os;
public abstract class AsyncTask<Params, Progress, Result>
{
public static enum Status
{
FINISHED(),
PENDING(),
RUNNING();
}
public  AsyncTask() {  }
public final  android.os.AsyncTask.Status getStatus() { return Status.FINISHED ;}
protected abstract  Result doInBackground(Params... params);
protected  void onPreExecute() {  }
@java.lang.SuppressWarnings(value={"UnusedDeclaration"})
protected  void onPostExecute(Result result) {  }
@java.lang.SuppressWarnings(value={"UnusedDeclaration"})
protected  void onProgressUpdate(Progress... values) {  }
@java.lang.SuppressWarnings(value={"UnusedParameters"})
protected  void onCancelled(Result result) {  }
protected  void onCancelled() {  }
public final  boolean isCancelled() {return false;  }
public final  boolean cancel(boolean mayInterruptIfRunning) {  return false;}
public final  Result get() throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {  return null;}
public final  Result get(long timeout, java.util.concurrent.TimeUnit unit) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException { return null; }
public final  android.os.AsyncTask<Params, Progress, Result> execute(Params... params) {  return null;}
public final  android.os.AsyncTask<Params, Progress, Result> executeOnExecutor(java.util.concurrent.Executor exec, Params... params) {  return null;}
public static  void execute(java.lang.Runnable runnable) {  }
protected final  void publishProgress(Progress... values) {  }
public static final java.util.concurrent.Executor THREAD_POOL_EXECUTOR;
public static final java.util.concurrent.Executor SERIAL_EXECUTOR;
static { THREAD_POOL_EXECUTOR = null; SERIAL_EXECUTOR = null; }
}
