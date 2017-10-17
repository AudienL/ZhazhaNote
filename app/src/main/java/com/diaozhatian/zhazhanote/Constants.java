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

    public final static String API_CLIENT_TYPE = "Android";

    public final static String URL_SMS_CODE = HOST + "sms/code.do";// 获取验证码
    public final static String URL_AUTH_REGISTER = HOST + "auth/register.do";// 注册
    public final static String URL_AUTH_LOGIN = HOST + "auth/login.do";// 登录
    public final static String URL_AUTH_CHANGE_PASSWORD = HOST + "auth/changePassword.do";// 修改密码
    public final static String URL_AUTH_RESET_PASSWORD = HOST + "auth/resetPassword.do";// 修改密码_验证码
    public final static String URL_UPDATE_LOGIN_INFO = HOST + "auth/updateLoginInfo.do";// 记录版本信息
//    public final static String URL_USER_GET_MY_INFO_BY_ID = HOST + "user/getMyInfoById/{userId}.do";// 个人资料
}
