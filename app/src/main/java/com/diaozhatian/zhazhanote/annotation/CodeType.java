package com.diaozhatian.zhazhanote.annotation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/19.
 */
@Retention(RetentionPolicy.SOURCE)
@StringDef({CodeType.REGISTER, CodeType.FORGOT_PASSWORD})
public @interface CodeType {
    String REGISTER = "register";
    String FORGOT_PASSWORD = "forget";
}
