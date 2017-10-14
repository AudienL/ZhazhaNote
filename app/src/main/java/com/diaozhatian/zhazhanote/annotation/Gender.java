package com.diaozhatian.zhazhanote.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/15.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({Gender.MALE, Gender.FEMALE})
public @interface Gender {
    String MALE = "男";
    String FEMALE = "女";
}
