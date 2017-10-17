package com.diaozhatian.zhazhanote.base;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;

import com.audienl.superlibrary.base.SuperActivity;

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
    }

    /** 检测并申请应用必须的权限，在应用入口调用 */
    public void requestGlobalPermissions() {
        runOnPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE, () -> {}, () -> {});
    }
}
