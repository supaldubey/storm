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

import in.cubestack.android.lib.storm.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define a Column for a table or entity declared with @Table annotation
 * 
 * @author Supal Dubey
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	/**
	 * The name of the Column that is desired for SQLite table. 
	 * For all practical purposes the name of entity property would be used, the name would be used by framework to generate underlying queries.
	 * 
	 */
    String name();

    /**
     * The logical most "relate-able" Java type for the column, this should match the defined field types. <br><br>
     * <code>@Column(type=FieldType.INTEGER)<br>private int count;</code> <br><br>should be used when field is defined as Integer or int.
     *  
     *  The implicit conversion works on the fieldtype, there are checks for auto conversions but works best if used precisely 
     */
    FieldType type();
    
    
    /**
     * For future plans, can be ignored now. 
     */
    boolean parentReferenceKey() default false;

    /**
     * The database version in which the column was added to the table. 
     * Storm has the capability to auto update your schema and tables. In case a new column needs to be added to existing tables, you may use this. 
     *  <br> <br>
     * <code>@Column(type=FieldType.INTEGER, addedVersion = 3)<br>private int count;</code> <br> <br>
     * 
     * In case the current database version matches the added version, Storm will add the column to your table.
     *   
     */
    int addedVersion() default 1;
}
