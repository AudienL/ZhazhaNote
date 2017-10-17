package com.diaozhatian.zhazhanote.http;

import com.audienl.superlibrary.utils.AndroidUtils;
import com.diaozhatian.zhazhanote.BuildConfig;
import com.diaozhatian.zhazhanote.Constants;
import com.diaozhatian.zhazhanote.annotation.Gender;
import com.diaozhatian.zhazhanote.base.App;
import com.diaozhatian.zhazhanote.bean.User;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import io.reactivex.Observable;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/11.
 */
public class Api {
    private static final String TAG = "Api";

    /** 个人资料 */
//    public static Observable<HttpResult> getMyInfo(int userId) {
//        return Observable.create(e -> {
//            final RequestParams params = new RequestParams(Constants.URL_USER_GET_MY_INFO_BY_ID);
//            JSONObject obj = new JSONObject();
//            obj.put("userId", userId);
//            params.setBodyContent(obj.toString());
//            new HttpHelper<>(HttpResult.class, e, "个人资料").post(params);
//        });
//    }

    /** 记录版本信息 */
    public static Observable<HttpResult> updateVersionInfo(int userId) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_UPDATE_LOGIN_INFO);
            JSONObject obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("apkVersion", BuildConfig.VERSION_CODE);
            obj.put("clientInfo", AndroidUtils.getDeviceUniqueId(App.instance));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "记录版本信息").post(params);
        });
    }

    /** 修改密码_验证码 */
    public static Observable<User> resetPasswordByPhoneCode(String mobile, String code, String password) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_RESET_PASSWORD);
            JSONObject obj = new JSONObject();
            obj.put("mobile", mobile);
            obj.put("code", code);
            obj.put("password", ApiEncryptUtils.encrypt(password));
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "修改密码_验证码").post(params);
        });
    }

    /** 修改密码 */
    public static Observable<User> resetPassword(int userId, String oldPassword, String newPassword) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_CHANGE_PASSWORD);
            JSONObject obj = new JSONObject();
            obj.put("userId", String.valueOf(userId));
            obj.put("oldPassword", ApiEncryptUtils.encrypt(oldPassword));
            obj.put("newPassword", ApiEncryptUtils.encrypt(newPassword));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "修改密码").post(params);
        });
    }

    /** 登录 */
    public static Observable<User> login(String mobile, String password) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_LOGIN);
            JSONObject obj = new JSONObject();
            obj.put("mobile", mobile);
            obj.put("password", ApiEncryptUtils.encrypt(password));
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "登录").post(params);
        });
    }

    /** 注册 */
    public static Observable<User> register(String mobile, String password, @Gender String gender, String smsCode) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_REGISTER);
            JSONObject obj = new JSONObject();
            obj.put("mobile", mobile);
            obj.put("sex", gender);
            obj.put("smsCode", smsCode);
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            obj.put("password", ApiEncryptUtils.encrypt(password));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "注册").post(params);
        });
    }

    /** 获取验证码 */
    public static Observable<HttpResult> getCode(String mobile) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_SMS_CODE);
            params.addQueryStringParameter("mobile", mobile);
            new HttpHelper<>(HttpResult.class, e, "获取验证码").get(params);
        });
    }
}
