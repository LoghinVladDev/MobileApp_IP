package com.example.ipapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilsSharedPreferences {
    private final static String PREFERENCE_FILE_KEY = "SPECTER_PREFERENCES";
    public final static String KEY_LOGGED_EMAIL = "EMAIL";
    public final static String KEY_LOGGED_PASSWORD = "PASSWORD";

    //<editor-fold desc="GET AND SET VALUE">
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }
    public static void setString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    //</editor-fold>
}
