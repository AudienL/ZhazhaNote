package com.diaozhatian.zhazhanote.manager;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.audienl.superlibrary.utils.SPUtils;
import com.diaozhatian.zhazhanote.base.App;
import com.diaozhatian.zhazhanote.bean.User;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/18.
 */
public class UserManager {
    private static final String TAG = "UserManager";

    private static User mLoginUser;

    public static void saveLoginUser(User user) {
        if (user == null) return;
        mLoginUser = user;
        String json = JSON.toJSONString(user);
        SPUtils.putString(App.instance, TAG, "user", json);
    }

    public static User getLoginUser() {
        if (mLoginUser != null && mLoginUser.token != null) return mLoginUser;
        String json = SPUtils.getString(App.instance, TAG, "user", null);
        if (TextUtils.isEmpty(json)) return null;
        mLoginUser = JSON.parseObject(json, User.class);
        return mLoginUser;
    }
}
