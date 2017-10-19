package com.diaozhatian.zhazhanote.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/19.
 */
public class RegularUtils {
    /**
     * 判断是不是一个合法的手机号码
     */
    public static boolean isPhone(CharSequence number) {
        if (TextUtils.isEmpty(number)) return false;
        Pattern pattern = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        return pattern.matcher(number).matches();
    }
}
