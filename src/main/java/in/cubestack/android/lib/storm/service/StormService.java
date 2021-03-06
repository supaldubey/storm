package in.cubestack.android.lib.storm.service;

import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.android.lib.storm.criteria.Projection;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.Restrictions;

import java.util.Collection;
import java.util.List;

/**
 * A core Android SQLite ORM framework build for speed and raw execution.
 * Copyright (c) 2014 CubeStack. Version built for Flash Back..
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
public interface StormService {

	public <E> Projection projectionFor(Class<E> entity) throws StormException;

	public <E> Restrictions restrictionsFor(Class<E> entity) throws StormException;

	public <E> void save(E entity) throws Exception;

	public <E> int update(E entity) throws Exception;

	public <E> int update(Collection<E> entities) throws Exception;

	/**
	 * Probably this can be customized by calling the below method and passing a
	 * criteria.
	 * <p/>
	 * But wanted to somehow keep both independent. Sounds stupid but lets go
	 * with this now.
	 *
	 * @param id
	 * @return
	 */
	public <E> E findById(Class<E> type, long id) throws Exception;

	public <E> int delete(E entity) throws Exception;

	public List<Object> project(Class<?> type, Restriction restriction, Projection projection) throws Exception;

	public <E> List<E> find(Class<E> type, Restriction restriction) throws Exception;

	public <E> E findOne(Class<E> type, Restriction restriction) throws Exception;

	public <E> int count(Class<E> type, Restriction restriction) throws Exception;

	<E> List<E> findAll(Class<E> type) throws Exception;

	<E> int truncateTable(Class<E> type) throws Exception;

	<E> int delete(Class<E> type, Restriction restriction) throws Exception;

	<E> int count(Class<E> type) throws Exception;

	List<Object[]> rawQuery(String query, String[] arguments) throws Exception;

	<E> List<E> find(Class<E> type, Restriction restriction, Order order) throws Exception;

	<E> List<E> findAll(Class<E> type, Order order) throws Exception;

	<E> void save(Collection<E> entities) throws Exception;

}