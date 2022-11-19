package com.customerprocessor.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static com.customerprocessor.constant.Constants.USER_DIRECTORY;

public class ApplicationProperties {
    private static final Path path = Paths.get(USER_DIRECTORY, "src/main/resources/application.properties");
    private static final ResourceBundle resource = ResourceBundle.getBundle(path.toFile().getPath());
    public static String getValue(String key){
        return resource.getString(key);
    }
}
