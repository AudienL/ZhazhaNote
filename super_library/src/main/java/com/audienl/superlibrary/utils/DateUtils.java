package com.audienl.superlibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/7/28.
 */
public class DateUtils {

//    public String getDateStrFriendly(Calendar c) {
//
//    }
//
//    public long getTimeToNow(Calendar c) {
//
//    }

    private static StringBuilder mStringBuilder = new StringBuilder();
    private static Formatter mFormatter = new Formatter(mStringBuilder, Locale.getDefault());

    /**
     * @param duration_ms 时长
     * @return 如果大于1小时，则返回：xx:xx:xx，否则返回：xx:xx
     */
    public static String formatDuration(long duration_ms) {
        long duration_s = duration_ms / 1000;

        long s = duration_s % 60;
        long m = (duration_s / 60) % 60;
        long h = duration_s / 3600;

        mStringBuilder.setLength(0);
        if (h > 0) {
            return mFormatter.format("%02d:%02d:%02d", h, m, s).toString();
        } else {
            return mFormatter.format("%02d:%02d", m, s).toString();
        }
    }

    /**
     * @param patten yyyy-MM-dd hh:mm:ss
     */
    public static String format(String patten, Calendar c) {
        return format(patten, c.getTime());
    }

    /**
     * @param patten yyyy-MM-dd hh:mm:ss
     */
    public static String format(String patten, long time_ms) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time_ms);
        return format(patten, c);
    }

    /**
     * @param patten yyyy-MM-dd hh:mm:ss
     */
    public static String format(String patten, Date date) {
        SimpleDateFormat format = new SimpleDateFormat(patten, Locale.CHINA);
        return format.format(date);
    }
}
