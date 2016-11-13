package com.series.numberseries.util;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class AppxMessages { 
    // property file is: package/name/messages.properties
    private static final String BUNDLE_NAME = "appxmessages";
    private static final ResourceBundle RESOURCE_BUNDLE = Utf8ResourceBundle.getBundle(BUNDLE_NAME);
    private AppxMessages() {
    }
    public static String getString(String key) {
        try {
            String val = RESOURCE_BUNDLE.getString(key);
           // System.out.println(val);
            return val;
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    public static String getString(String key, Object... params  ) {
        try {
            ResourceBundle RESOURCE_BUNDLE = Utf8ResourceBundle.getBundle(BUNDLE_NAME);
            String val = RESOURCE_BUNDLE.getString(key);
         //   System.out.println(val);
            return MessageFormat.format(val, params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}