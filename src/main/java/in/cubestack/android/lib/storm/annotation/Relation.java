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

import in.cubestack.android.lib.storm.CascadeTypes;
import in.cubestack.android.lib.storm.FetchType;

/**
 * @Relation annotation can be used to provide One to one or One to Many relations to an entity.
 *  <br><br>
 * <pre>
 * class Parent {
 * 		@PrimaryKey
 * 		@Column(name="id", type=LONG)
 * 		private int id;
 * 		
 * 		@Relation(joinColumn="parentId", targetEntity = Child.class})
 * 		private Child child;
 * }
 * </pre>
 * <br><br>
 * This can be used to declare both as list or single entity. Note that in this case Child entity should have a property with name parentId.<br>
 * Parent Id would be used by Storm to generate Joins and queries to fetch the data from database.
 * 
 * @author Supal Dubey
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Relation {
	
	/**
	 * The column corresponding to the target entity property. This ideally is the column that defines the Many to One relationships.
	 * This should be a valid property mapped with @COlumn annotation in the target entity.
	 * 
	 * For example for Employee - Department relation entities, departmentId in Employee entity should be the Join column.
	 * 
	 *  
	 * @return the property corresponding to Column on which Join has to be performed.
	 */
	String joinColumn();
    Class<?> targetEntity();
    CascadeTypes[] cascade() default {CascadeTypes.PERSIST, CascadeTypes.DELETE};
    
    /**
     * For future use only. <br>Do not override this to Lazy as of now, as of now we do not have a way to load the information later.. 
     */
    FetchType fetchType() default FetchType.EAGER;
    
    /**
	 * The column corresponding to the parent entity property. This should be a valid property mapped with @Column annotation in the class where @Relation is being used.
	 * That means this is the column on the parent defining the entity.
	 * 
	 * If not provided this would default to the primary key of the defining entity as a fallback.
	 * 
	 * For example for Employee - Department relation entities, the primary key in Department entity should be the Join column. 
	 * Or department Id from Employee should match the ID property in department.
	 * 
	 *  
	 * @return the property corresponding to Column which defines the Join to be performed.
	 */
    String joinOnColumn() default "" ;
}
