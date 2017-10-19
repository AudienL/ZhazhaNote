package com.diaozhatian.zhazhanote.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.bean.event.RequestLoginEvent;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.Toolbar;
import com.pgyersdk.activity.FeedbackActivity;
import com.pgyersdk.feedback.PgyFeedback;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {

    @ViewInject(R.id.toolbar) Toolbar mToolbar;
    @ViewInject(R.id.btnLogout) Button mBtnLogout;
    @ViewInject(R.id.btnFeedback) Button mBtnFeedback;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void feedback() {
        ToastUtils.showToast(mBaseActivity, "不允许权限不能录音");
        runOnPermissionGranted(Manifest.permission.RECORD_AUDIO, () -> {
            // 需要录音的话则去要权限
            FeedbackActivity.setBarImmersive(true);
            PgyFeedback.getInstance().showActivity(mBaseActivity);
        }, () -> {
            // 不通过录音权限也可以反馈，只是不能录音
            FeedbackActivity.setBarImmersive(true);
            PgyFeedback.getInstance().showActivity(mBaseActivity);
        });
    }

    private void logout() {
        UserManager.logout().subscribe(ok -> {
            EventBus.getDefault().post(new RequestLoginEvent());
        }, throwable -> {});
    }

    @Event(value = {R.id.btnLogout, R.id.btnFeedback})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
                // 注销
                logout();
                break;
            case R.id.btnFeedback:
                // 反馈
                feedback();
                break;
        }
    }
}
