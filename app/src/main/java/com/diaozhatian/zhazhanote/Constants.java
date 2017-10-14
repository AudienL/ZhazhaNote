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

    public final static String RSA_PUBLIC_EXPONENT = "65537";
    public final static String RSA_MODULUS = "107920443544455768016677584168539218636510423190166749858207053111985549971685450681854223707183979763669480082097213075685642473312627579352159815373238385671496948488830337447224954167005534404920894925171926220403339853330972872444943790512686025398908275326951007383446583337775901191019706911982355423473";

    public final static String URL_SMS_CODE = HOST + "sms/code.do";// 获取验证码
    public final static String URL_AUTH_REGISTER = HOST + "auth/register.do";// 注册
}
