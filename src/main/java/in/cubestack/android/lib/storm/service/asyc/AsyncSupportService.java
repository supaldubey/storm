/**
 * 
 */
package in.cubestack.android.lib.storm.service.asyc;

import in.cubestack.android.lib.storm.core.DatabaseMetaData;
import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.android.lib.storm.criteria.Order;
import in.cubestack.android.lib.storm.criteria.Projection;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.Restrictions;
import in.cubestack.android.lib.storm.service.BaseService;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

/**
 * @author Supal Dubey
 * 
 *         A core Android SQLite ORM framrwork build for speed and raw
 *         execution. Copyright (c) 2014 CubeStack. Version built for Flash
 *         Back..
 *         <p/>
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *         <p/>
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *         <p/>
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 *         BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 *         ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *         CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *         SOFTWARE.
 */

public class AsyncSupportService {

	private BaseService baseService;

	public AsyncSupportService(Context context, DatabaseMetaData databaseMetaData) {
		baseService = new BaseService(context, databaseMetaData);
	}

	public <T> void save(final T entity, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, T>() {

			private Throwable throwable;

			@Override
			protected T doInBackground(Void... arg0) {
				try {
					baseService.save(entity);
				} catch (Exception e) {
					throwable = e;
				}
				return entity;
			}

			protected void onPostExecute(T result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onSave(entity);
				}
			};

		}.execute();
	}

	public <T> void saveAll(final List<T> entity, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, List<T>>() {

			private Throwable throwable;

			@Override
			protected List<T> doInBackground(Void... arg0) {
				try {
					baseService.save(entity);
				} catch (Exception e) {
					throwable = e;
				}
				// Basically Storm would update the same list
				return entity;
			}

			protected void onPostExecute(List<T> result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onSaveAll(entity);
				}
			};

		}.execute();
	}

	public <T> void update(final T entity, final StormCallBack<T> callBack) {

		new AsyncTask<Void, Void, Integer>() {

			private Throwable throwable;

			@Override
			protected Integer doInBackground(Void... arg0) {
				int updated = 0;
				try {
					updated = baseService.update(entity);
				} catch (Exception e) {
					throwable = e;
				}
				return updated;
			}

			protected void onPostExecute(Integer result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onUpdate(result);
				}
			};

		}.execute();
	}

	public <T> void update(final List<T> entity, final StormCallBack<T> callBack) {

		new AsyncTask<Void, Void, Integer>() {

			private Throwable throwable;

			@Override
			protected Integer doInBackground(Void... arg0) {
				int updated = 0;
				try {
					updated = baseService.update(entity);
				} catch (Exception e) {
					throwable = e;
				}
				return updated;
			}

			protected void onPostExecute(Integer result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onUpdate(result);
				}
			};

		}.execute();
	}

	public <T> void findById(final Class<T> type, final long id, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, T>() {

			private Throwable throwable;

			@Override
			protected T doInBackground(Void... arg0) {
				T entity = null;

				try {
					entity = baseService.findById(type, id);
				} catch (Exception e) {
					throwable = e;
				}
				return entity;
			}

			protected void onPostExecute(T result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onSingleRow(result);
				}
			};

		}.execute();
	}

	public <T> void truncate(final Class<T> type, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, Integer>() {

			private Throwable throwable;

			@Override
			protected Integer doInBackground(Void... arg0) {
				int deleted = 0;
				try {
					deleted = baseService.truncateTable(type);
				} catch (Exception e) {
					throwable = e;
				}
				return deleted;
			}

			protected void onPostExecute(Integer result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onDelete(result);
				}
			};

		}.execute();
	}

	public <E> void delete(final Class<E> type, final Restriction restriction, final StormCallBack<E> callBack) {
		new AsyncTask<Void, Void, Integer>() {

			private Throwable throwable;

			@Override
			protected Integer doInBackground(Void... arg0) {
				int deleted = 0;
				try {
					deleted = baseService.delete(type, restriction);
				} catch (Exception e) {
					throwable = e;
				}
				return deleted;
			}

			protected void onPostExecute(Integer result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onDelete(result);
				}
			};

		}.execute();
	}

	public <E> void delete(final E entity, final StormCallBack<E> callBack) {
		new AsyncTask<Void, Void, Integer>() {

			private Throwable throwable;

			@Override
			protected Integer doInBackground(Void... arg0) {
				int deleted = 0;
				try {
					deleted = baseService.delete(entity);
				} catch (Exception e) {
					throwable = e;
				}
				return deleted;
			}

			protected void onPostExecute(Integer result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onDelete(result);
				}
			};

		}.execute();
	}

	public void project(final Class<Object> type, final Restriction restriction, final Projection projection, final StormCallBack<Object> callBack) {
		new AsyncTask<Void, Void, List<Object>>() {

			private Throwable throwable;

			@Override
			protected List<Object> doInBackground(Void... arg0) {
				List<Object> results = null;
				try {
					results = baseService.project(type, restriction, projection);
				} catch (Exception e) {
					throwable = e;
				}
				return results;
			}

			protected void onPostExecute(List<Object> result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onResults(result);
				}
			};

		}.execute();
	}

	public void rawQuery(final String query, final String[] arguments, final StormCallBack<Object[]> callBack) {
		new AsyncTask<Void, Void, List<Object[]>>() {

			private Throwable throwable;

			@Override
			protected List<Object[]> doInBackground(Void... arg0) {
				List<Object[]> results = null;
				try {
					results = baseService.rawQuery(query, arguments);
				} catch (Exception e) {
					throwable = e;
				}
				return results;
			}

			protected void onPostExecute(List<Object[]> result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onResults(result);
				}
			};

		}.execute();
	}

	public <T> void find(final Class<T> type, final Restriction restriction, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, List<T>>() {

			private Throwable throwable;

			@Override
			protected List<T> doInBackground(Void... arg0) {
				List<T> results = null;
				try {
					results = baseService.find(type, restriction);
				} catch (Exception e) {
					throwable = e;
				}
				return results;
			}

			protected void onPostExecute(List<T> result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onResults(result);
				}
			};

		}.execute();
	}

	public <T> void find(final Class<T> type, final Restriction restriction, final Order order, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, List<T>>() {

			private Throwable throwable;

			@Override
			protected List<T> doInBackground(Void... arg0) {
				List<T> results = null;
				try {
					results = baseService.find(type, restriction, order);
				} catch (Exception e) {
					throwable = e;
				}
				return results;
			}

			protected void onPostExecute(List<T> result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onResults(result);
				}
			};

		}.execute();
	}

	public <T> void findOne(final Class<T> type, final Restriction restriction, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, T>() {

			private Throwable throwable;

			@Override
			protected T doInBackground(Void... arg0) {
				T result = null;
				try {
					result = baseService.findOne(type, restriction);
				} catch (Exception e) {
					throwable = e;
				}
				return result;
			}

			protected void onPostExecute(T result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onSingleRow(result);
				}
			};

		}.execute();
	}

	public <T> void findAll(final Class<T> type, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, List<T>>() {

			private Throwable throwable;

			@Override
			protected List<T> doInBackground(Void... arg0) {
				List<T> results = null;
				try {
					results = baseService.findAll(type);

				} catch (Exception e) {
					throwable = e;
				}
				return results;
			}

			protected void onPostExecute(List<T> results) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onResults(results);
				}
			};
		}.execute();
	}

	public <T> void findAll(final Class<T> type, final Order order, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, List<T>>() {

			private Throwable throwable;

			@Override
			protected List<T> doInBackground(Void... arg0) {
				List<T> results = null;
				try {
					results = baseService.findAll(type, order);

				} catch (Exception e) {
					throwable = e;
				}
				return results;
			}

			protected void onPostExecute(List<T> results) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onResults(results);
				}
			};
		}.execute();
	}

	public <E> void count(final Class<E> type, final StormCallBack<E> callBack) {
		new AsyncTask<Void, Void, Integer>() {

			private Throwable throwable;

			@Override
			protected Integer doInBackground(Void... arg0) {
				int count = 0;
				try {
					count = baseService.count(type);
				} catch (Exception e) {
					throwable = e;
				}
				return count;
			}

			protected void onPostExecute(Integer result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onAggregate(result);
				}
			};

		}.execute();
	}

	public <T> Projection projectionFor(Class<T> clazz) throws StormException {
		return baseService.projectionFor(clazz);
	}

	public <T> Restrictions restrictionsFor(Class<T> clazz) throws StormException {
		return baseService.restrictionsFor(clazz);
	}

	public <E> void count(final Class<E> type, final Restriction restriction, final StormCallBack<E> callBack) {
		new AsyncTask<Void, Void, Integer>() {

			private Throwable throwable;

			@Override
			protected Integer doInBackground(Void... arg0) {
				int count = 0;
				try {
					count = baseService.count(type, restriction);
				} catch (Exception e) {
					throwable = e;
				}
				return count;
			}

			protected void onPostExecute(Integer result) {
				if (throwable != null) {
					callBack.onError(throwable);
				} else {
					callBack.onAggregate(result);
				}
			};

		}.execute();
	}

}
