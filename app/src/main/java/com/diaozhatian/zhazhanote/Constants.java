package com.diaozhatian.zhazhanote;

/**
 * 常量
 * Created by feiwei on 2016/5/20.
 */
public class Constants {
    public static String HOST;

    static {
        if (BuildConfig.DEBUG) {
            // PC直接安装
            HOST = "http://hellogood.top/hellogood_api/";

        } else {
            // 打包-发布
            HOST = "http://hellogood.top/hellogood_api/";
        }
    }

    public final static String URL_SMS_CODE = HOST + "sms/code.do";// 获取验证码
    public final static String URL_AUTH_REGISTER = HOST + "auth/register.do";// 注册
}
