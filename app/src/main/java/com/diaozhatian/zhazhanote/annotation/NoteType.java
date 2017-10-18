package com.diaozhatian.zhazhanote.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/18.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({NoteType.DAY, NoteType.WEEK, NoteType.MONTH, NoteType.SEASON, NoteType.YEAR})
public @interface NoteType {
    String DAY = "日";
    String WEEK = "周";
    String MONTH = "月";
    String SEASON = "季";
    String YEAR = "年";
}
