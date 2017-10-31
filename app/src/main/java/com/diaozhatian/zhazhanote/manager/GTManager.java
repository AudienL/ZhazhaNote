package com.diaozhatian.zhazhanote.manager;

import android.content.Context;

import com.audienl.superlibrary.utils.SPUtils;
import com.diaozhatian.zhazhanote.base.App;
import com.igexin.sdk.PushManager;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/31.
 */
public class GTManager {
    public static void init(Context applicationContext) {
        PushManager.getInstance().initialize(applicationContext, GTService.class);
        PushManager.getInstance().registerPushIntentService(applicationContext, GTReceiver.class);
    }

    public static void setCID(String cid) {
        SPUtils.putString(App.instance, "push_cid", cid);
    }

    public static String getCID() {
        return SPUtils.getString(App.instance, "push_cid", null);
    }
}
