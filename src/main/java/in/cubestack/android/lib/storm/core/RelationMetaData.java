/**
 * 
 */
package in.cubestack.android.lib.storm.core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import in.cubestack.android.lib.storm.CascadeTypes;
import in.cubestack.android.lib.storm.FetchType;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2014-15  CubeStack. Built for performance, scalability and ease to use.
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
public class RelationMetaData {
	
	private Class<?> targetEntity;
	private CascadeTypes[] cascadeTypes;
	private String joinColumn;
	private String joinOnColumn;
	private String property;
	private String alias;
	private Class<?> backingImplementation;
	//Default
	private boolean collectionBacked= true;
	
	private FetchType fetchType;
	
	public Class<?> getTargetEntity() {
		return targetEntity;
	}
	public void setTargetEntity(Class<?> targetEntity) {
		this.targetEntity = targetEntity;
	}
	public CascadeTypes[] getCascadeTypes() {
		return cascadeTypes;
	}
	public void setCascadeTypes(CascadeTypes[] cascadeTypes) {
		this.cascadeTypes = cascadeTypes;
	}
	
	/**
	 * The column corresponding to the target entity property. This ideally is the column that defines the Many to One relationships.
	 * 
	 * For example for Employee - Department relation entities, departmentId in Employee entity should be the Join column.
	 * 
	 *  
	 * @return the property corresponding to Column on which Join has to be performed.
	 */
	public String getJoinColumn() {
		return joinColumn;
	}
	public void setJoinColumn(String joinColumn) {
		this.joinColumn = joinColumn;
	}
	
	/**
	 * The column corresponding to the parent entity property.
	 * 
	 * For example for Employee - Department relation entities, the primary key in Department entity should be the Join column. 
	 * Or department Id from Employee should match the ID property in department.
	 * 
	 *  
	 * @return the property corresponding to Column which defines the Join to be performed.
	 */
	public String getJoinOnColumn() {
		return joinOnColumn;
	}
	
	
	public void setJoinOnColumn(String dbJoinColumn) {
		this.joinOnColumn = dbJoinColumn;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public FetchType getFetchType() {
		return fetchType;
	}
	public void setFetchType(FetchType fetchType) {
		this.fetchType = fetchType;
	}
	public Class<?> getBackingImplementation() {
		return backingImplementation;
	}
	public void setBackingImplementation(Class<?> backingImplementation) {
		if(backingImplementation.isAssignableFrom(Set.class)) {
			this.backingImplementation = HashSet.class;
		} else if(backingImplementation.isAssignableFrom(List.class)) {
			this.backingImplementation = LinkedList.class;
		} else {
			setCollectionBacked(false);
			this.backingImplementation = targetEntity;
		}
		
	}
	public boolean isCollectionBacked() {
		return collectionBacked;
	}
	public void setCollectionBacked(boolean collectionBacked) {
		this.collectionBacked = collectionBacked;
	}
}
