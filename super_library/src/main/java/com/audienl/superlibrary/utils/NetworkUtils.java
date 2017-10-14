package com.audienl.superlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

/**
 * @author audienl@qq.com on 2016/7/14.
 */
public class NetworkUtils {
    /**
     * 判断当前网络是否是wifi网络
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断网络是否可用
     * 需要权限：android.permission.ACCESS_NETWORK_STATE
     */
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) return null;
        return manager.getActiveNetworkInfo();
    }

    /**
     * 跳转到无线网络设置
     */
    public static void startWirelessSettingActivity(Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

    /**
     * 跳转到wifi设置
     */
    public static void startWifiSettingActivity(Context context) {
        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }
}
