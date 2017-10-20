package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.Toolbar;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.etUsername) EditText mEtUsername;
    @BindView(R.id.etPassword) EditText mEtPassword;
    @BindView(R.id.btnLogin) Button mBtnLogin;
    @BindView(R.id.btnRegister) Button mBtnRegister;
    @BindView(R.id.btnForgetPassword) Button mBtnForgetPassword;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
    }

    @Override
    public void initListeners() {
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

    @OnClick(value = {R.id.btnLogin, R.id.btnRegister, R.id.btnForgetPassword})
    public void onClick(View view) {
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
