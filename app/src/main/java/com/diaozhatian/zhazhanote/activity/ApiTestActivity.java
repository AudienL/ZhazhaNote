package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.annotation.Gender;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.http.Api;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_api_test)
public class ApiTestActivity extends BaseActivity {
    private static final String TAG = "ApiTestActivity";

    public static void start(Context context) {
        Intent starter = new Intent(context, ApiTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(value = {R.id.register, R.id.login, R.id.getMyInfo, R.id.getCode, R.id.changePassword, R.id.changePasswordByCode, R.id.updateVersionInfo})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.getCode:
                // 获取验证码
                Api.getCode("17098905192").subscribe(result -> {}, throwable -> {});
                break;
            case R.id.register:
                // 注册
                Api.register("17098905192", "123456", Gender.MALE, "690917").subscribe(httpResult -> {}, throwable -> { });
                break;
            case R.id.login:
                // 登录
                Api.login("17098905192", "123456").subscribe(user -> {}, throwable -> {});
                break;
            case R.id.changePassword:
                // 修改密码
                Api.resetPassword(3150, "123456", "pppppp").subscribe(user -> {}, throwable -> { });
                break;
            case R.id.changePasswordByCode:
                // 修改密码_根据验证码
                Api.resetPasswordByPhoneCode("17098905192", "690917", "oooooo").subscribe(user -> {}, throwable -> {});
                break;
            case R.id.updateVersionInfo:
                // 记录版本信息
                Api.updateVersionInfo(3150).subscribe(user -> {}, throwable -> {});
                break;
            case R.id.getMyInfo:
                // 个人资料
//                Api.getMyInfo(3150).subscribe(user -> {}, throwable -> {});
                break;
        }
    }
}