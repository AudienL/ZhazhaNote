package com.diaozhatian.zhazhanote.manager;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.audienl.superlibrary.utils.SPUtils;
import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.base.App;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.bean.event.RequestLoginEvent;
import com.diaozhatian.zhazhanote.http.Api;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/18.
 */
public class UserManager {
    private static final String TAG = "UserManager";

    private static User mLoginUser;

    /** 全局统一登录接口 */
    public static Observable<User> login(String mobile, String password) {
        return Api.login(mobile, password).map(user -> {
            UserManager.saveLoginUser(user);
            return user;
        });
    }

    /** 全局统一注销接口 */
    public static Observable<Boolean> logout() {
        return Observable.create(e -> {
            mLoginUser = null;
            SPUtils.remove(App.instance, TAG, "user");
            e.onNext(true);
        });
    }

    /**
     * @return 如果返回null，代表未登录
     */
    public static User checkUserLogin() {
        User user = UserManager.getLoginUser();
        if (user == null) {
            ToastUtils.showToast(App.instance, "请先登录");
            EventBus.getDefault().post(new RequestLoginEvent());
            return null;
        }
        return user;
    }

    public static void saveLoginUser(User user) {
        if (user == null) return;
        mLoginUser = user;
        String json = JSON.toJSONString(user);
        SPUtils.putString(App.instance, TAG, "user", json);
    }

    /**
     * 返回 null 说明未登录
     */
    public static User getLoginUser() {
        if (mLoginUser != null && mLoginUser.token != null) return mLoginUser;
        String json = SPUtils.getString(App.instance, TAG, "user", null);
        if (TextUtils.isEmpty(json)) return null;
        mLoginUser = JSON.parseObject(json, User.class);
        return mLoginUser;
    }
}
