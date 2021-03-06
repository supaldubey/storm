/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2016 CubeStack. Built for performance, scalability and ease
 * to use.
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package in.cubestack.apps.android.storm.entitites;

import in.cubestack.android.lib.storm.FieldType;
import in.cubestack.android.lib.storm.annotation.Column;
import in.cubestack.android.lib.storm.annotation.PrimaryKey;
import in.cubestack.android.lib.storm.annotation.Table;

/**
 * Test entity demonstrating the relation with TestEntity.
 * 
 * @author Supal Dubey
 *
 */
@Table(name="CHILD")
public class TestChild {
	
	public TestChild() {
	}
	
	
	public TestChild(long id, String name, long parId) {
		this.id = id;
		this.name =name;
		this.parentId = parId;
	}
	
	
	
	@PrimaryKey
	@Column(name="ID", type=FieldType.INTEGER )
	private long id;

	
	@Column(name="childNam", type=FieldType.TEXT)
	private String name;
	
	
	@Column(name="PAR_ID", type=FieldType.LONG)
	private long parentId;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getParentId() {
		return parentId;
	}


	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	
	
}
