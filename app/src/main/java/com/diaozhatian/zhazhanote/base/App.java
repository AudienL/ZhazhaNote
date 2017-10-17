package com.diaozhatian.zhazhanote.base;

import com.audienl.superlibrary.base.SuperApplication;
import com.audienl.superlibrary.utils.LogUtils;
import com.diaozhatian.zhazhanote.BuildConfig;
import com.pgyersdk.crash.PgyCrashManager;

import org.xutils.x;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/13.
 */
public class App extends SuperApplication {
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtils.init(BuildConfig.DEBUG, "Zha");

        PgyCrashManager.register(this);
        x.Ext.init(this);
        x.Ext.setDebug(false);
    }
}
