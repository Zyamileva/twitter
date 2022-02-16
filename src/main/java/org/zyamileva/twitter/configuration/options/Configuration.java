package org.zyamileva.twitter.configuration.options;

public class Configuration {
    private DaoTypeOption daoTypeOption;
    private boolean populateDB;

    public DaoTypeOption getDaoTypeOption() {
        return daoTypeOption;
    }

    public boolean isPopulateDB() {
        return populateDB;
    }

    public Configuration(DaoTypeOption daoTypeOption, boolean populateDB) {
        this.daoTypeOption = daoTypeOption;
        this.populateDB = populateDB;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "daoTypeOption=" + daoTypeOption +
                ", populateDB=" + populateDB +
                '}';
    }
}