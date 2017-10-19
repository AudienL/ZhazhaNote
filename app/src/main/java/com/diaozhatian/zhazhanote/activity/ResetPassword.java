package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.annotation.CodeType;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.utils.RegularUtils;
import com.diaozhatian.zhazhanote.widget.Toolbar;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.lang.ref.WeakReference;

@ContentView(R.layout.activity_reset_password)
public class ResetPassword extends BaseActivity {

    @ViewInject(R.id.toolbar) Toolbar mToolbar;
    @ViewInject(R.id.etMobile) EditText mEtMobile;
    @ViewInject(R.id.etPassword) EditText mEtPassword;
    @ViewInject(R.id.btnGetCode) Button mBtnGetCode;
    @ViewInject(R.id.etCode) EditText mEtCode;
    @ViewInject(R.id.btnOk) Button mBtnOk;

    public static void start(Context context) {
        Intent starter = new Intent(context, ResetPassword.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar.setOnBackButtonClickListener(view -> finish());
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
        Api.getCode(mobile, CodeType.FORGOT_PASSWORD).subscribe(result -> {
            ToastUtils.showToast(mBaseActivity, "发送成功");
            new CodeHandler(mBtnGetCode).sendEmptyMessage(0);
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
            mBtnGetCode.setEnabled(true);
        });
    }

    private void handleBtnOk() {
        mBtnOk.setEnabled(false);

        String mobile = mEtMobile.getText().toString();
        String password = mEtPassword.getText().toString();
        String code = mEtCode.getText().toString();

        if (!RegularUtils.isPhone(mobile)) {
            ToastUtils.showToast(mBaseActivity, "请输入正确的手机号");
            mBtnOk.setEnabled(true);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtils.showToast(mBaseActivity, "密码不能为空");
            mBtnOk.setEnabled(true);
            return;
        }

        Api.resetPasswordByPhoneCode(mobile, code, password).subscribe(user -> {
            ToastUtils.showToast(mBaseActivity, "重置密码成功，请登录");
            finish();
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
            mBtnOk.setEnabled(true);
        });
    }

    @Event(value = {R.id.btnGetCode, R.id.btnOk})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGetCode:
                // 验证码
                handleGetCode();
                break;
            case R.id.btnOk:
                // 确定
                handleBtnOk();
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
