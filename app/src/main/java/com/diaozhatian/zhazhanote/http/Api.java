package com.diaozhatian.zhazhanote.http;

import com.audienl.superlibrary.utils.AndroidUtils;
import com.diaozhatian.zhazhanote.BuildConfig;
import com.diaozhatian.zhazhanote.Constants;
import com.diaozhatian.zhazhanote.annotation.Gender;
import com.diaozhatian.zhazhanote.annotation.NoteType;
import com.diaozhatian.zhazhanote.base.App;
import com.diaozhatian.zhazhanote.bean.Note;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.manager.UserManager;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/11.
 */
public class Api {
    private static final String TAG = "Api";

    /** 删除便签 */
    public static Observable<HttpResult> deleteNote(int id) {
        return Observable.create((ObservableOnSubscribe<HttpResult>) e -> {
            String url = String.format(Locale.CHINA, Constants.URL_NOTE_DELETE_BY_ID, id);
            final RequestParams params = new RequestParams(url);
            new HttpHelper<>(HttpResult.class, e, "删除便签").get(params);
        });
    }

    /**
     * 设置便签状态
     * @param status 1为有效，0为隐藏
     */
    public static Observable<HttpResult> updateNoteStatus(int id, int status) {
        return Observable.create((ObservableOnSubscribe<HttpResult>) e -> {
            String url = String.format(Locale.CHINA, Constants.URL_NOTE_SET_STATUS_BY_ID, id, status);
            final RequestParams params = new RequestParams(url);
            new HttpHelper<>(HttpResult.class, e, "设置便签状态").get(params);
        });
    }

    /**
     * 修改便签
     * @param color #FFFFFF
     */
    public static Observable<HttpResult> updateNote(String userId, String content, @NoteType String type, String color) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_NOTE_UPDATE);
            JSONObject obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("phoneUniqueCode", AndroidUtils.getDeviceUniqueId(App.instance));
            obj.put("content", content);
            obj.put("type", type);
            obj.put("color", color);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "修改便签").post(params);
        });
    }

    /**
     * 新增便签
     * @param color #FFFFFF
     */
    public static Observable<HttpResult> addNote(String userId, String content, @NoteType String type, String color) {
        return Observable.create(e -> {
            final RequestParams params = new RequestParams(Constants.URL_NOTE_ADD);
            JSONObject obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("phoneUniqueCode", AndroidUtils.getDeviceUniqueId(App.instance));
            obj.put("content", content);
            obj.put("type", type);
            obj.put("color", color);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(HttpResult.class, e, "新增便签").post(params);
        });
    }

    /**
     * 便签列表
     */
    public static Observable<List<Note>> getNoteList(String userId, @NoteType String type, int page, int pageSize) {
        return Observable.create((ObservableOnSubscribe<Note>) e -> {
            final RequestParams params = new RequestParams(Constants.URL_NOTE_GET_NOTE_LIST);
            JSONObject obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("phoneUniqueCode", AndroidUtils.getDeviceUniqueId(App.instance));
            obj.put("type", type);
            obj.put("page", String.valueOf(page));
            obj.put("pageSize", String.valueOf(pageSize));
            params.setBodyContent(obj.toString());
            new HttpHelper<>(Note.class, e, "便签列表").post(params);
        }).map(note -> note.dataList);
    }

    /** 个人资料 */
    public static Observable<User> getUserInfo(int userId, String token) {
        return Observable.create((ObservableOnSubscribe<User>) e -> {
            String url = String.format(Locale.CHINA, Constants.URL_USER_GET_MY_INFO_BY_ID, userId);
            final RequestParams params = new RequestParams(url);
            new HttpHelper<>(User.class, e, "个人资料").get(params);
        }).map(user -> {
            user = user.data;
            UserManager.saveLoginUser(user);
            return user;
        });
    }

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
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "修改密码").post(params);
        });
    }

    /** 登录 */
    public static Observable<User> login(String mobile, String password) {
        return Observable.create((ObservableOnSubscribe<User>) e -> {
            final RequestParams params = new RequestParams(Constants.URL_AUTH_LOGIN);
            JSONObject obj = new JSONObject();
            obj.put("mobile", mobile);
            obj.put("password", ApiEncryptUtils.encrypt(password));
            obj.put("clientType", Constants.API_CLIENT_TYPE);
            params.setBodyContent(obj.toString());
            new HttpHelper<>(User.class, e, "登录").post(params);
        }).map(user -> {
            UserManager.saveLoginUser(user);
            return user;
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
