package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.Toolbar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.toolbar) Toolbar mToolbar;
    @ViewInject(R.id.etUsername) EditText mEtUsername;
    @ViewInject(R.id.etPassword) EditText mEtPassword;
    @ViewInject(R.id.btnLogin) Button mBtnLogin;
    @ViewInject(R.id.btnRegister) Button mBtnRegister;
    @ViewInject(R.id.btnForgetPassword) Button mBtnForgetPassword;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void handleLogin() {
        String mobile = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        UserManager.login(mobile, password).subscribe(user -> {
            ToastUtils.showToast(mBaseActivity, "登录成功");
            MainActivity.start(mBaseActivity);
            finish();
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
        });
    }

    @Event(value = {R.id.btnLogin, R.id.btnRegister, R.id.btnForgetPassword})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                // 登录
                handleLogin();
                break;
            case R.id.btnRegister:
                // 注册
                RegisterActivity.start(mBaseActivity);
                break;
            case R.id.btnForgetPassword:
                // 忘记密码
                ResetPassword.start(mBaseActivity);
                break;
        }
    }
}
