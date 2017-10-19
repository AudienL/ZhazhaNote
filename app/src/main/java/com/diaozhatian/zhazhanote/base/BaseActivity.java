package com.diaozhatian.zhazhanote.base;

import android.os.Bundle;
import android.os.Handler;

import com.audienl.superlibrary.base.SuperActivity;
import com.diaozhatian.zhazhanote.activity.LoginActivity;
import com.diaozhatian.zhazhanote.activity.MainActivity;
import com.diaozhatian.zhazhanote.bean.event.RequestLoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.x;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/13.
 */
public class BaseActivity extends SuperActivity {
    protected BaseActivity mBaseActivity;
    protected Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mBaseActivity = this;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onRequestLogin(RequestLoginEvent event) {
        if (this instanceof MainActivity) {
            LoginActivity.start(mBaseActivity);
        }
        finish();
    }
}
