package com.audienl.superlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author audienl@qq.com on 2016/7/14.
 */
public class SPUtils {
    private static final String PREFERENCES_NAME = "yika";

    /**
     * 网关和用户的绑定关系
     */
    public static final String NAME_GATEWAY_USER = "gateway_user";

    // --- 通过指定文件读取 ---
    public static void putBoolean(Context context, String spName, String key, boolean value) {
        getSharedPreferences(context, spName).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String spName, String key, boolean defaultValue) {
        return getSharedPreferences(context, spName).getBoolean(key, defaultValue);
    }

    public static void putString(Context context, String spName, String key, String value) {
        getSharedPreferences(context, spName).edit().putString(key, value).commit();
    }

    public static String getString(Context context, String spName, String key, String defaultValue) {
        return getSharedPreferences(context, spName).getString(key, defaultValue);
    }

    public static void putInt(Context context, String spName, String key, int value) {
        getSharedPreferences(context, spName).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String spName, String key, int defaultValue) {
        return getSharedPreferences(context, spName).getInt(key, defaultValue);
    }

    public static void putFloat(Context context, String spName, String key, float value) {
        getSharedPreferences(context, spName).edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context context, String spName, String key, float defaultValue) {
        return getSharedPreferences(context, spName).getFloat(key, defaultValue);
    }

    public static void putLong(Context context, String spName, String key, long value) {
        getSharedPreferences(context, spName).edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String spName, String key, long defaultValue) {
        return getSharedPreferences(context, spName).getLong(key, defaultValue);
    }

    public static boolean remove(Context context, String spName, String key) {
        return getSharedPreferences(context, spName).edit().remove(key).commit();
    }

    private static SharedPreferences getSharedPreferences(Context context, String spName) {
        return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    // --- 默认 ---
    public static void putBoolean(Context context, String key, boolean value) {
        getSharedPreferences(context).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void putString(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getSharedPreferences(context).getString(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        getSharedPreferences(context).edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    public static void putFloat(Context context, String key, float value) {
        getSharedPreferences(context).edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        return getSharedPreferences(context).getFloat(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        getSharedPreferences(context).edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return getSharedPreferences(context).getLong(key, defaultValue);
    }

    public static boolean remove(Context context, String key) {
        return getSharedPreferences(context).edit().remove(key).commit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
