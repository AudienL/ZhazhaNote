package com.audienl.superlibrary.utils;

import android.content.Context;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/6/15.
 */
@Deprecated
public class DateUtils2 extends android.text.format.DateUtils {
    /**
     * 返回如：59分钟前
     * @param time                 some time in the past.
     * @param minResolution        the minimum elapsed time (in milliseconds) to report
     *                             when showing relative times. For example, a time 3 seconds in
     *                             the past will be reported as "0 minutes ago" if this is set to
     *                             {@link #MINUTE_IN_MILLIS}.
     * @param transitionResolution the elapsed time (in milliseconds) at which
     *                             to stop reporting relative measurements. Elapsed times greater
     *                             than this resolution will default to normal date formatting.
     *                             For example, will transition from "7 days ago" to "Dec 12"
     *                             when using {@link #WEEK_IN_MILLIS}.
     */
    public static String getRelativeDateTimeString(Context c, long time, long minResolution, long transitionResolution) {
        String str = getRelativeDateTimeString(c, time, minResolution, transitionResolution, FORMAT_SHOW_TIME).toString();
        return str.split(" ")[0];
    }
}
