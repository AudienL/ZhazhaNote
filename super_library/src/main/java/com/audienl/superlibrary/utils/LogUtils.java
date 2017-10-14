package com.audienl.superlibrary.utils;

import android.app.Application;
import android.util.Log;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/5/11.
 */
public class LogUtils {

    private static boolean DEBUG = true;
    private static String TAG = "";

    /**
     * 建议在 {@link Application#onCreate()} 中的第一行调用。
     * @param DEBUG 是否答应log消息，建议传入 BuildConfig.DEBUG
     * @param TAG   打印消息的TAG，建议传入项目名
     */
    public static void init(boolean DEBUG, String TAG) {
        LogUtils.DEBUG = DEBUG;
        LogUtils.TAG = TAG;
    }

    public static void info(Object... objs) {
        log("", Log.INFO, objs);
    }

    public static void info(String tag, Object... objs) {
        log(tag, Log.INFO, objs);
    }

    public static void error(Object... objs) {
        log("", Log.ERROR, objs);
    }

    public static void error(String tag, Object... objs) {
        log(tag, Log.ERROR, objs);
    }

    private static void log(String tag, int level, Object[] objs) {
        if (objs == null || objs.length == 0) return;

        StringBuilder sb = new StringBuilder();
        sb.append("【").append(tag).append("】 ");

        // 需要的时候自己解释Object
        for (Object obj : objs) {
            sb.append(obj).append(" | ");
        }

        if (DEBUG) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(TAG, sb.toString());
                    break;
                case Log.INFO:
                    Log.i(TAG, sb.toString());
                    break;
                case Log.ERROR:
                    Log.e(TAG, sb.toString());
                    break;
                default:
                    Log.e(TAG, sb.toString());
            }
        }
    }
}
