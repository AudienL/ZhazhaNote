package com.diaozhatian.zhazhanote.http;

import com.diaozhatian.zhazhanote.Constants;

import org.xutils.http.RequestParams;

import io.reactivex.Observable;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/11.
 */
public class Api {
    /** 注册 */
//    public static Observable<HttpResult> postRegister(String mobile, String password) {
//        final RequestParams params = new RequestParams(Constants.URL_AUTH_REGISTER);
//        params.addBodyParameter("mobile", mobile);
//        PublicKey key = new
//        params.addBodyParameter("password", password);
//        params.addBodyParameter("sex", sex);
//        params.addBodyParameter("smsCode", smsCode);
//        params.addBodyParameter("clientType", clientType);
//        return Observable.create(e -> new Http<>(HttpResult.class, e, "注册").get(params));
//    }

    /** 获取验证码 */
    public static Observable<HttpResult> getCode(String mobile) {
        final RequestParams params = new RequestParams(Constants.URL_SMS_CODE);
        params.addQueryStringParameter("mobile", mobile);
        return Observable.create(e -> new Http<>(HttpResult.class, e, "获取验证码").get(params));
    }
}
