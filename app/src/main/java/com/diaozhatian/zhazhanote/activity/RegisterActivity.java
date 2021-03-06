package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.annotation.CodeType;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.utils.RegularUtils;
import com.diaozhatian.zhazhanote.widget.Toolbar;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.etPassword) EditText mEtPassword;
    @BindView(R.id.etMobile) EditText mEtMobile;
    @BindView(R.id.btnGetCode) TextView mBtnGetCode;
    @BindView(R.id.etCode) EditText mEtCode;
    @BindView(R.id.btnRegister) TextView mBtnRegister;

    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
    }

    @Override
    public void initListeners() {
        mToolbar.setOnLeftButtonClickListener(view -> finish());
    }

    private void handleGetCode() {
        mBtnGetCode.setEnabled(false);
        String mobile = mEtMobile.getText().toString();
        if (!RegularUtils.isPhone(mobile)) {
            ToastUtils.showToast(mBaseActivity, "请输入正确的手机号");
            mBtnGetCode.setEnabled(true);
            return;
        }
        // 请求后台
        Api.getCode(mobile, CodeType.REGISTER).subscribe(result -> {
            ToastUtils.showToast(mBaseActivity, "发送成功");
            new CodeHandler(mBtnGetCode).sendEmptyMessage(0);
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
            mBtnGetCode.setEnabled(true);
        });
    }

    private void handleRegister() {
        mBtnRegister.setEnabled(false);

        String mobile = mEtMobile.getText().toString();
        String password = mEtPassword.getText().toString();
        String code = mEtCode.getText().toString();

        if (!RegularUtils.isPhone(mobile)) {
            ToastUtils.showToast(mBaseActivity, "请输入正确的手机号");
            mBtnRegister.setEnabled(true);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(mBaseActivity, "密码不能为空");
            mBtnRegister.setEnabled(true);
            return;
        }

        Api.register(mobile, password, code).subscribe(user -> {
            ToastUtils.showToast(mBaseActivity, "注册成功，请登录");
            finish();
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
            mBtnRegister.setEnabled(true);
        });
    }

    @OnClick(value = {R.id.btnGetCode, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGetCode:
                // 验证码
                handleGetCode();
                break;
            case R.id.btnRegister:
                // 注册
                handleRegister();
                break;
        }
    }

    /** 验证码倒计时 */
    private static class CodeHandler extends Handler {
        int count = 120;
        WeakReference<TextView> mButton;

        CodeHandler(TextView button) {
            mButton = new WeakReference<>(button);
        }

        void onCountOver() {
            mButton.get().setText("获取验证码");
            mButton.get().setEnabled(true);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mButton.get() != null) {
                if (count == 0) {
                    onCountOver();
                    return;
                }

                mButton.get().setText(count + "s");
                count--;
                sendEmptyMessageDelayed(0, 1000);
            }
        }
    }
}
