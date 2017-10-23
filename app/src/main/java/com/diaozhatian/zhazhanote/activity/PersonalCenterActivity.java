package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;

import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.bean.event.RequestLoginEvent;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

import static com.diaozhatian.zhazhanote.manager.UserManager.checkUserLogin;

public class PersonalCenterActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    public static void start(Context context) {
        User user = UserManager.checkUserLogin();
        if (user != null) {
            // 已登录
            Intent starter = new Intent(context, PersonalCenterActivity.class);
            context.startActivity(starter);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_personal_center;
    }

    @Override
    public void init() {

    }

    @Override
    public void initListeners() {
        mToolbar.setOnLeftButtonClickListener(view -> finish());
    }

    private void logout() {
        UserManager.logout().subscribe(ok -> {
            EventBus.getDefault().post(new RequestLoginEvent());
        }, throwable -> {});
    }

    @OnClick(R.id.item_logout)
    public void onViewClicked() {
        logout();
    }
}
