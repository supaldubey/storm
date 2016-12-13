package in.cubestack.android.lib.storm.core;

import in.cubestack.android.lib.storm.lifecycle.DatabaseUpdatesHandler;

/**
 * Created by supal on 10/10/14.
 */
public class DatabaseMetaData {

    private String name;
    private int version;
    private Class<?>[] tables;
    private Class<? extends DatabaseUpdatesHandler> handler;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Class<?>[] getTables() {
        return tables;
    }

    public void setTables(Class<?>[] tables) {
        this.tables = tables;
    }

	public Class<? extends DatabaseUpdatesHandler> getHandler() {
		return handler;
	}

	public void setHandler(Class<? extends DatabaseUpdatesHandler> handler) {
		this.handler = handler;
	}
}
