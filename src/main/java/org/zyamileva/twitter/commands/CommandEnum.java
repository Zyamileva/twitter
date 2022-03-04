package org.zyamileva.twitter.commands;

public enum CommandEnum {
    CREATE_USER("Create user"),
    UPDATE_USER("Update user"),
    FIND_ALL_USERS("Find all users"),
    FIND_USER_BY_ID("Find user by id"),
    FIND_USER_BY_LOGIN("Find user by login"),
    MAIN_COMMANDS("Show main commands"),
    EXIT("Exit");

    private final String value;

    CommandEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}