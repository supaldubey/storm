/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2016  CubeStack. Built for performance, scalability and ease to use.
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
package in.cubestack.android.lib.storm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import in.cubestack.android.lib.storm.lifecycle.LifeCycleHandler;


/**
 *
 * LIfeCycle can be used to consume events triggered during entity lifecycle. <br><br>The Handler declared in below implementation would receive calls from the service
 * on different lifecycle events. <br><br>
 * This may be used to control if an entity should be persisted or should be deleted externally. <br>
 * 
 * For instance, this may act as a Global rule or validation for an entity. <br><br>For example:<br>
 * 
 * <pre>
 * 	public boolean preSave(Entity entity) {
 * 		//If time is in past, and entity has past details do not allow to save
 * 		if(isTimePast(entity)) {
 * 			return false;
 * 		}
 * 	}
 *</pre>
 * 
 * @author Supal Dubey
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LifeCycle {

	Class<? extends LifeCycleHandler<?>> handler();
	
	/**
	 * Defined for Future use. 
	 */
	boolean async() default false;
}
