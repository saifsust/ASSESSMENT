package com.customerprocessor.util;


import java.util.ResourceBundle;

public class SourceProperty {
    private static final String RESOURCE = "application";
    private final ResourceBundle resource;
    private static SourceProperty SOURCE_PROPERTY = new SourceProperty();

    private SourceProperty() {
        this.resource = ResourceBundle.getBundle(RESOURCE);
    }

    public static SourceProperty getInstance() {
        return SOURCE_PROPERTY;
    }

    public String getProperty(String key) {
        return this.resource.getString(key);
    }
}
