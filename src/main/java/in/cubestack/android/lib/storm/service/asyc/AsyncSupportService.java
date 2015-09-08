/**
 * 
 */
package in.cubestack.android.lib.storm.service.asyc;

import in.cubestack.android.lib.storm.core.DatabaseMetaData;
import in.cubestack.android.lib.storm.core.StormException;
import in.cubestack.android.lib.storm.criteria.Projection;
import in.cubestack.android.lib.storm.criteria.Restriction;
import in.cubestack.android.lib.storm.criteria.Restrictions;
import in.cubestack.android.lib.storm.service.BaseService;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

/**
 * @author supal
 *
 */
public class AsyncSupportService  {
	
	private BaseService baseService;

	public AsyncSupportService(Context context, DatabaseMetaData databaseMetaData) {
		baseService = new BaseService(context, databaseMetaData);
	}

	
	public <T> void save(final T entity, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					baseService.save(entity);
					callBack.onSave(entity);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	

	public <T> void saveAll(final List<T> entity, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					baseService.save(entity);
					callBack.onSaveAll(entity);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	
	
	public <T> void update(final T entity, final StormCallBack<T> callBack) {
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					int updated = baseService.update(entity);
					callBack.onUpdate(updated);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	

	public <T> void update(final List<T> entity, final StormCallBack<T> callBack) {
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					int updated = baseService.update(entity);
					callBack.onUpdate(updated);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	
	
	public <T> void findById(final Class<T> type, final long id, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					T entity = baseService.findById(type, id);
					callBack.onSingleRow(entity);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	

	public <T> void truncate(final Class<T> type, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					int deleted = baseService.truncateTable(type);
					callBack.onDelete(deleted);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	
	
	
	public <E> void delete(final Class<E> type, final Restriction restriction, final StormCallBack<E> callBack)  {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					int deleted = baseService.delete(type, restriction);
					callBack.onDelete(deleted);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}

	
	public <E> void delete(final E entity, final StormCallBack<E> callBack)  {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					int deleted = baseService.delete(entity);
					callBack.onDelete(deleted);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}

	
	public  void project(final Class<Object> type, final Restriction restriction, final Projection projection, final StormCallBack<Object> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					List<Object> results = baseService.project(type, restriction, projection);
					callBack.onResults(results);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	
	public <T> void find(final Class<T> type, final Restriction restriction, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					List<T> results = baseService.find(type, restriction);
					callBack.onResults(results);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}

	
	public <T> void findOne(final Class<T> type, final Restriction restriction, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					T result = baseService.findOne(type, restriction);
					callBack.onSingleRow(result);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}

	
	public <T> void findAll(final Class<T> type, final StormCallBack<T> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					List<T> results = baseService.findAll(type);
					callBack.onResults(results);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	
	
	public <E> void count(final Class<E> type, final StormCallBack<E> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					int count = baseService.count(type);
					callBack.onAggregate(count);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}

	
	public <T> Projection projectionFor(Class<T> clazz) throws StormException {
		return baseService.projectionFor(clazz);
	}
	
	
	public <T> Restrictions restrictionsFor(Class<T> clazz) throws StormException {
		return baseService.restrictionsFor(clazz);
	}
	
	public <E> void count(final Class<E> type, final Restriction restriction, final StormCallBack<E> callBack) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				try {
					int count = baseService.count(type, restriction);
					callBack.onAggregate(count);
				} catch (Exception e) {
					callBack.onError(e);
				}
				return null;
			}
		};
	}
	
}
