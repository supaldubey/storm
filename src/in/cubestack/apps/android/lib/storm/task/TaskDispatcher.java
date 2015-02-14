/**
 * 
 */
package in.cubestack.apps.android.lib.storm.task;

import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleEnvent;
import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleHandler;

import java.lang.reflect.Method;

import android.os.AsyncTask;



/**
 * @author arunk
 *
 */
public class TaskDispatcher extends AsyncTask<Object, Void, Void> {

	private LifeCycleHandler<?> lifeCycleHandler;
	private LifeCycleEnvent cycleEnvent;
	
	public TaskDispatcher(LifeCycleHandler<?> lifeCycleHandler, LifeCycleEnvent event) {
		this.lifeCycleHandler = lifeCycleHandler;
		this.cycleEnvent = event;
	}
	
	@Override
	protected Void doInBackground(Object... params){
		Method[] methods = lifeCycleHandler.getClass().getMethods();
		for(Method method: methods) {
			if(method.getName().equals(cycleEnvent.method())) {
				try {
					method.invoke(cycleEnvent, params);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
