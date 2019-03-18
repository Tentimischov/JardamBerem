package com.baktiyar.android.jardamberem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.text.TextUtils;

import com.baktiyar.android.jardamberem.StartApplication;
import com.google.gson.Gson;

import java.util.Locale;

import static com.baktiyar.android.jardamberem.utils.UtilsKt.e;


public class Settings {
    private static final String RUSSIAN = "ru";
    public static final  String KYRGYZ = "ky";
    private static final String SETTINGS_STORAGE_NAME = "jardam.berem";
    private static final String LANGUAGE = "language";
    private static final String MESSAGE = "message";
    private static final String CONTACTS = "contacts";
    private static final String USER_COORDINATES = "user_position";
    private static final String CITY_NAME_ARRAY = "CITY_ARRAY";
    private static final String CITY_ID_ARRAY = "CITY_ID_ARRAY";
    private static final String NO_CITY_FOUND = "No city";
    private static final String CITY_ID = "CITY_ID";
    private static final String CATEGORY = "CATEGORY";
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String CATEGORY_DEF = "Все";
    private static final String SPINNER_ITEM = "SPINNER_ITEM";
    private static final String AGREEMENT = "AGREEMENT";


    private static int getInt(String key, int defValue) {
        return getPrefs(StartApplication.INSTANCE.getApplicationContext()).getInt(key, defValue);
    }
    private static boolean getBoolean(String key, boolean defValue) {
        return getPrefs(StartApplication.INSTANCE.getApplicationContext()).getBoolean(key, defValue);
    }

    public static long getLong(String key, long defValue) {
        return getPrefs(StartApplication.INSTANCE.getApplicationContext()).getLong(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return getPrefs(StartApplication.INSTANCE.getApplicationContext()).getString(key, defValue);
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SETTINGS_STORAGE_NAME, StartApplication.INSTANCE.getApplicationContext().MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return StartApplication.INSTANCE.getApplicationContext().getSharedPreferences(SETTINGS_STORAGE_NAME, StartApplication.INSTANCE.getApplicationContext().MODE_PRIVATE).edit();
    }
    public static void setCityNameArray(String data) {
        getEditor().putString(CITY_NAME_ARRAY, data).commit();
    }
    public static String getCityNameArray() {
        return getString(CITY_NAME_ARRAY, NO_CITY_FOUND);
    }

    public static void setCityIdArray(String data) {
        getEditor().putString(CITY_ID_ARRAY, data).commit();
    }
    public static String getCityIdArray() {
        return getString(CITY_ID_ARRAY, "0");
    }
    /*
     * agreement
     */

    public static void setAgreement(Boolean b) {
        getEditor().putBoolean(AGREEMENT, b).commit();
    }

    public static boolean getAgreement() {
        return getBoolean(AGREEMENT, false);
    }


    /*
     * Language
     */
    public static String getLanguage() {
        return getString(LANGUAGE, RUSSIAN);
    }

    public static void setLanguage(String language) {
        getEditor().putString(LANGUAGE, language).apply();
    }

    public static void setCityId(Integer id) {
        getEditor().putInt(CITY_ID, id).commit();
    }
    public static int getCityId() {
        return getInt(CITY_ID, 1);
    }

    public static void setCategory(String s) {
        getEditor().putString(CATEGORY, s).commit();
    }
    public static void setCategory(String mKey, String value) {
        getEditor().putString(mKey, value).apply();
    }

    public static String getCategory(String mKey) {
        return getString(mKey, "no category");
    }

    public static String getCategory() {
        return getString(CATEGORY, CATEGORY_DEF);
    }

    public static void setCategoryId(Integer i) {
        getEditor().putInt(CATEGORY_ID, i).commit();
    }

    public static int getCategoryId() {
        return getInt(CATEGORY_ID, 1);
    }

    public static void setSpinnerItemPosition(Integer id) {
        getEditor().putInt(SPINNER_ITEM, id).commit();
    }
    public static int getSpinnerItemPosition() {
        return getInt(SPINNER_ITEM, 0);
    }



    //localization
    public static void setLocale(Locale locale) {
        Configuration config = StartApplication.INSTANCE.getApplicationContext().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale);
        else
            config.locale = locale;
        config.locale = locale;
        StartApplication.INSTANCE.getApplicationContext().getResources().updateConfiguration(config, StartApplication.INSTANCE.getApplicationContext().getResources().getDisplayMetrics());
    }





}