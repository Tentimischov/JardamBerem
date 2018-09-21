package com.baktiyar.android.jardamberem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.Locale;


public class Settings {
    private static final String RUSSIAN = "ru";
    public static final String KYRGYZ = "ky";
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


    public static int getInt(Context context, String key, int defValue) {
        return getPrefs(context).getInt(key, defValue);
    }
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getPrefs(context).getBoolean(key, defValue);
    }

    public static long getLong(Context context, String key, long defValue) {
        return getPrefs(context).getLong(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        return getPrefs(context).getString(key, defValue);
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(SETTINGS_STORAGE_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(SETTINGS_STORAGE_NAME, Context.MODE_PRIVATE).edit();
    }
    public static void setCityNameArray(Context context, String data) {
        getEditor(context).putString(CITY_NAME_ARRAY, data).commit();
    }
    public static String getCityNameArray(Context context) {
        return getString(context, CITY_NAME_ARRAY, NO_CITY_FOUND);
    }

    public static void setCityIdArray(Context context, String data) {
        getEditor(context).putString(CITY_ID_ARRAY, data).commit();
    }
    public static String getCityIdArray(Context context) {
        return getString(context, CITY_ID_ARRAY, "0");
    }
    /*
     * agreement
     */

    public static void setAgreement(Context context, Boolean b) {
        getEditor(context).putBoolean(AGREEMENT, b).commit();
    }

    public static boolean getAgreement(Context context) {
        return getBoolean(context, AGREEMENT, false);
    }


    /*
     * Language
     */
    public static String getLanguage(Context context) {
        return getString(context, LANGUAGE, RUSSIAN);
    }

    public static void setLanguage(Context context, String language) {
        getEditor(context).putString(LANGUAGE, language).apply();
    }

    public static void setCityId(Context context, Integer id) {
        getEditor(context).putInt(CITY_ID, id).commit();
    }
    public static int getCityId(Context context) {
        return getInt(context, CITY_ID, 1);
    }

    public static void setCategory(Context context, String s) {
        getEditor(context).putString(CATEGORY, s).commit();
    }
    public static String getCategory(Context context) {
        return getString(context, CATEGORY, CATEGORY_DEF);
    }

    public static void setCategoryId(Context context, Integer i) {
        getEditor(context).putInt(CATEGORY_ID, i).commit();
    }

    public static int getCategoryId(Context context) {
        return getInt(context, CATEGORY_ID, 1);
    }

    public static void setSpinnerItemPosition(Context context, Integer id) {
        getEditor(context).putInt(SPINNER_ITEM, id).commit();
    }
    public static int getSpinnerItemPosition(Context context) {
        return getInt(context, SPINNER_ITEM, 0);
    }

    /*
     * Language
     */
   /* public static ArrayList<Contact> getContacts(Context context) {
        String json = getString(context, CONTACTS, "");
        return new Gson().fromJson(json, new TypeToken<ArrayList<Contact>>() {
        }.getType());
    }

    public static void setContacts(Context context, List<Contact> data) {
        String json = data == null ? "" : new Gson().toJson(data);
        getEditor(context).putString(CONTACTS, json).commit();
    }*/

    public static String getMessage(Context context) {
        return getString(context, MESSAGE, null);
    }

    public static void setMessage(Context context, String message) {
        getEditor(context).putString(MESSAGE, message).commit();
    }

    public static Location getUserCoords(Context context) {
        String json = getPrefs(context).getString(USER_COORDINATES, null);
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, Location.class);
        }
        return null;
    }

    public static void setUserCoords(Context context, Location location) {
        if (location != null) {
            String json = new Gson().toJson(location);
            getEditor(context).putString(USER_COORDINATES, json).apply();
        }
    }

    //localization
    public static void setLocale(Context context, Locale locale) {
        Configuration config = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale);
        else
            config.locale = locale;
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }





}