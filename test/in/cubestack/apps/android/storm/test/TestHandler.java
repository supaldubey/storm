package in.cubestack.apps.android.storm.test;

import in.cubestack.apps.android.lib.storm.lifecycle.LifeCycleHandler;

public class TestHandler implements LifeCycleHandler<TestEntity>{

	@Override
	public boolean preSave(TestEntity entity) {
		return false;
	}

	@Override
	public void postSave(TestEntity entity, Throwable throwable) {
		
	}

	@Override
	public boolean preDelete(TestEntity entity) {
		return false;
	}

	@Override
	public void postDelete(TestEntity entity, Throwable throwable) {
		
	}

}
