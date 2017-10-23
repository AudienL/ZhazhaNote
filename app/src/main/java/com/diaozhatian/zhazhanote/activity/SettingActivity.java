package com.diaozhatian.zhazhanote.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.widget.Toolbar;
import com.pgyersdk.activity.FeedbackActivity;
import com.pgyersdk.feedback.PgyFeedback;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
    }

    @Override
    public void initListeners() {
        mToolbar.setOnLeftButtonClickListener(view -> finish());
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

    @OnClick({R.id.item_personal_center, R.id.item_favor, R.id.item_finished, R.id.item_help, R.id.item_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_personal_center:
                // 个人中心
                PersonalCenterActivity.start(mBaseActivity);
                break;
            case R.id.item_favor:
                // 收藏夹
                FavorNoteListActivity.start(mBaseActivity);
                break;
            case R.id.item_finished:
                // 已完成
                FinishedNoteListActivity.start(mBaseActivity);
                break;
            case R.id.item_help:
                // 帮助与反馈
                ToastUtils.showToast(mBaseActivity, "等待后台给页面URL");
                break;
            case R.id.item_about:
                // 关于
                ToastUtils.showToast(mBaseActivity, "等待后台给页面URL");
                break;
        }
    }
}
