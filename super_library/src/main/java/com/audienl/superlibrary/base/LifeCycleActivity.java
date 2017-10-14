package com.audienl.superlibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.audienl.superlibrary.utils.LogUtils;

/**
 * @author audienl@qq.com on 2016/7/14.
 */
public class LifeCycleActivity extends AppCompatActivity {

    private void log(String msg) {
        LogUtils.info("LifeCycle", this.getClass().getSimpleName() + " -> " + msg);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        log("onNewIntent: intent = " + intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate : savedInstanceState = " + savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        log("onRestart");
    }
}
