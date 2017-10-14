package com.audienl.superlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

/**
 * @author audienl@qq.com on 2016/7/14.
 */
public class UIUtils {
    private static DisplayMetrics metrics;
    private static int densityDpi = -1;
    private static float density = -1;

    static {
        metrics = new DisplayMetrics();
    }

    /**
     * 当前是否是横屏
     */
    public static boolean isScreenLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取屏幕DPI。
     * mdpi:160
     * hdpi:240
     * xhdpi:320
     * xxhdpi:480
     */
    public static int getDensityDpi(Activity activity) {
        if (densityDpi == -1) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            densityDpi = metrics.densityDpi;
        }
        return densityDpi;
    }

    /**
     * mdpi:1
     * hdpi:1.5
     * xhdpi:2
     * xxhdpi:3
     */
    public static float getDensity(Activity activity) {
        if (density == -1) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            density = metrics.density;
        }
        return density;
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
