/**
 * 
 */
package in.cubestack.apps.android.lib.storm.lifecycle;

/**
 * @author arunk
 *
 */
public enum LifeCycleEnvent {

	PRE_SAVE("preSave"),
	POST_SAVE("postSave"),
	PRE_DELETE("preDelete"),
	POST_DELETE("postDelete");
	
	private String method;

	LifeCycleEnvent(String method) {
		this.method = method;
	}

	public String method() {
		return method;
	}
}
