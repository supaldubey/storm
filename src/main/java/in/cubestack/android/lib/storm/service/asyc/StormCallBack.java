/**
 * 
 */
package in.cubestack.android.lib.storm.service.asyc;

import java.util.List;

/**
 * @author supal
 *
 */
public class StormCallBack {
	
	public <T> void onResults(List<T> results) {
		
	}
	
	public <T> void onSingleRow(T entity) {
		
	}
	
	public <T> void onSave(T entity) {
		
	}
	
	public <T> void onSaveAll(List<T> entity) {
		
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
