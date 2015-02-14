/**
 * 
 */
package in.cubestack.apps.android.lib.storm.task;

import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleEnvent;
import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleHandler;

import java.lang.reflect.Method;

import android.os.AsyncTask;



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
