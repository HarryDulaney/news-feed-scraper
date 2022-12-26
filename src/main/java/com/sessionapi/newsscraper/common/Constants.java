package com.sessionapi.newsscraper.common;

public class Constants {
    public static final String JDBC_BASE_URL = "jdbc:";
    public static final String SELECT_TYPE_ID = "id";
    public static final String SELECT_TYPE_ELEMENT = "element";
    public static final String SELECT_TYPE_CLASS = "class";
    public static final String SELECT_TYPE_XPATH = "xpath";


    private Constants () throws IllegalAccessException {
        throw new IllegalAccessException("Illegal access by trying to instantiate a utility class.");
    }
}