/**
 * 
 */
package in.cubestack.android.lib.storm.service.asyc;

import java.util.List;

/**
 * @author supal
 *
 */
public class StormCallBack<T> {
	
	public void onResults(List<T> results) {
		
	}
	
	public void onSingleRow(T entity) {
		
	}
	
	public void onSave(T entity) {
		
	}
	
	public void onSaveAll(List<T> entity) {
		
	}
	
	
	public void onDelete(int deletedRows) {
		
	}
	
	public void onUpdate(int updated) {
		
	}
	
	public void onError(Throwable throwable){
		
	}
	
	public void onAggregate(int result) {
		
	}

}
