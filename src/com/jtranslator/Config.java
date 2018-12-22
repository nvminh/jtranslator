package com.jtranslator;

import java.util.prefs.Preferences;

public enum  Config {
    GOOGLE_TRANSLATOR("https://translate.google.com/#auto/vi/"),
    ONLINE_DICT_URL;

    private Config() {

    }
    private Config(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    private String defaultValue;

    void set(String value) {
        PREFS.put(name(), value);
    }

    String get() {
        return PREFS.get(name(), defaultValue);
    }

    public static Preferences PREFS = Preferences.userNodeForPackage(Config.class);
}
