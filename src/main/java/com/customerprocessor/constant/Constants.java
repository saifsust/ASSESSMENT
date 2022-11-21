package com.customerprocessor.constant;

public class Constants {
    public static final String USER_DIRECTORY = System.getProperty("user.dir");
    public static final String DRIVER = "jdbc.drive";
    public static final String URL = "jdbc.url";
    public static final String USER_NAME = "jdbc.username";
    public static final String PASSWORD = "jdbc.password";
    public static final int PARTITION_SIZE = 10000;
    public static final int FILE_WRITE_PARTITION_SIZE = 10000;
    private Constants(){ }
}
