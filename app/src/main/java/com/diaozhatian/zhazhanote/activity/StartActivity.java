package com.diaozhatian.zhazhanote.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;

public class StartActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_start;
    }

    @Override
    public void init() {
        initPermissions();
    }

    @Override
    public void initListeners() {
    }

    private void initFinished() {
//        User user = UserManager.getLoginUser();
//        if (user == null) {
//            // 需要登录
//            LoginActivity.start(mBaseActivity);
//        } else {
//            MainActivity.start(mBaseActivity);
//        }
//        MainActivity.start(mBaseActivity);
        MainActivity2.start(mBaseActivity);
        finish();
    }

    private void initPermissions() {
        requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE).subscribe(ok -> {
            initFinished();
        }, throwable -> {
            new AlertDialog.Builder(mBaseActivity).setTitle("温馨提示").setTitle("必须允许权限才能使用APP").setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setPositiveButton("重新申请权限", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    initPermissions();
                }
            }).show();
        });
    }
}
