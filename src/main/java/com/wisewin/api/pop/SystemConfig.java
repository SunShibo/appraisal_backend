package com.wisewin.api.pop;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by wangyubo on 2018/3/18.
 */
public class SystemConfig {
    private static final String BUNDLE_NAME = "system_config";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private SystemConfig() {
    }

    /**
     * get the value from the properties file
     *
     * @param key the key in the properties file
     * @return
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return "no " + key + " key!";
        }
    }
}
