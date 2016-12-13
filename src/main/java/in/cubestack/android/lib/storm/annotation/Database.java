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

import in.cubestack.android.lib.storm.lifecycle.BasicDatabaseUpdateHandler;
import in.cubestack.android.lib.storm.lifecycle.DatabaseUpdatesHandler;

/**
 * 
 * This annotation defines the master Meta data for any database to be used within Storm. <br>
 * To declare new tables and the version to be managed by Storm, this annotation needs to be provided to the services. 
 * 
 * <br><br> <code> 
 * 
 * @Database(name="STORM_DB", tables = {Entity.class, AnotherEntity.class, version=3})<br>
 * public class DatabaseDeclaration {}
 * </code>
 * <br><br>
 * You may also choose to have more control on what happens once update or create has been run by Storm by providing implementation to <code> DatabaseUpdatesHandler.class </code>
 * <br>
 * The class DatabaseUpdatesHandler would be called after the internal update or create has been completed. <br><br>
 * It is critical to include all the proposed tables to the annotation to take advantage of Fail-Fast checks in case any details are missing.  
 * 
 * @author Supal Dubey
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Database {

	/**
	 * The version of the SQLIte database, this version is passed directly to SQLIteOpenerHelper and hence manages the lifecycle and udpates.<br>
	 * In case of upgrades to your application, please change the database version for the annotation to take advantage of Auto Updates.
	 */
    int version() default 1;

    /**
     * The logical name of the database to be passed down to SQLite
     */
    String name();

    /**
     * The list of managed entities that are a part of this Database. 
     * <br> All the tables defined or supposed to work should be added to this list. 
     */
    Class<?>[] tables();
    
    /**
     * In case of new installation or Upgrades to the application, Storm passes a callback to the below class in case more control is needed. <br>
     * 
     * <br> For instance; if you need to clean up the old data once update is performed you may implement <code>DatabaseUpdatesHandler</code> class or extend 
     *  <code>BasicDatabaseUpdateHandler</code> class and implement the <code>onUpgrade</code> method.
     */
    Class<? extends DatabaseUpdatesHandler> handler() default BasicDatabaseUpdateHandler.class;
}
