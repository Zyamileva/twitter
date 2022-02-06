package org.zyamileva.twitter.configuration.options;

public enum DaoTypeOption {
    IN_MEMORY("inem"),JDBC("jdbc");

    public static final String DAO_TYPE_OPTION_SHORT = "dt";
    public static final String DAO_TYPE_OPTION_LONG = "daoType";
    public static final String DAO_TYPE_OPTION_DESCRIPTION = "DaoType for twitter.";
    private final String value;

    DaoTypeOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}