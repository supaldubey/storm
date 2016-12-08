package in.cubestack.apps.android.storm.entitites;

import in.cubestack.android.lib.storm.lifecycle.LifeCycleHandler;

public class TestHandler implements LifeCycleHandler<TestEntity>{

	@Override
	public boolean preSave(TestEntity entity) {
		return true;
	}

	@Override
	public void postSave(TestEntity entity, Throwable throwable) {
		
	}

	@Override
	public boolean preDelete(TestEntity entity) {
		return true;
	}

	@Override
	public void postDelete(TestEntity entity, Throwable throwable) {
		
	}

}
