package com.diaozhatian.zhazhanote.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.pgyersdk.activity.FeedbackActivity;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.update.PgyUpdateManager;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 申请权限
        requestGlobalPermissions();

        // 检查更新
        PgyUpdateManager.register(this, "com.diaozhatian.zhazhanote.pugongying_provider");

        // TODO: 2017/10/17
        ApiTestActivity.start(mBaseActivity);
    }

    /** 反馈 */
    public void onFeedback(View view) {
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
}
