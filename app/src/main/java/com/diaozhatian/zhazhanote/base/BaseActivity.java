package com.diaozhatian.zhazhanote.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;

import com.audienl.superlibrary.base.SuperActivity;
import com.diaozhatian.zhazhanote.activity.LoginActivity;
import com.diaozhatian.zhazhanote.activity.MainActivity;
import com.diaozhatian.zhazhanote.bean.event.RequestLoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/13.
 */
public abstract class BaseActivity extends SuperActivity {
    protected BaseActivity mBaseActivity;
    protected Handler mHandler = new Handler();
    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseActivity = this;
        EventBus.getDefault().register(this);

        setContentView(getLayoutResId());
        unbinder = ButterKnife.bind(this);

        init();
        initListeners();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Subscribe
    public void onRequestLogin(RequestLoginEvent event) {
        if (this instanceof MainActivity) {
            LoginActivity.start(mBaseActivity);
        }
        finish();
    }

    @LayoutRes
    public abstract int getLayoutResId();

    public abstract void init();

    public abstract void initListeners();

    public void loadData() {}
}
