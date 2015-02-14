package in.cubestack.apps.android.lib.storm.core;

/**
 * Created by supal on 10/10/14.
 */
public class DatabaseMetaData {

    private String name;
    private int version;
    private Class<?>[] tables;

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
}
