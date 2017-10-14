package com.audienl.superlibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.audienl.superlibrary.R;

import java.lang.ref.WeakReference;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/5/15.
 */
public class WaitDialog extends Dialog {
    private static final String TAG = "WaitDialog";

    private TextView mTvTimeout = null;

    private TimeoutHandler mTimeoutHandler;

    /**
     * @param cancelable 是否通过按返回键取消dialog。
     */
    public WaitDialog(@NonNull Context context, boolean cancelable) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.setCancelable(cancelable);

//        this.setCanceledOnTouchOutside(true);// 并没有什么卵用
    }

    // 调用show方法之后才会调用onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_dialog);

        mTvTimeout = (TextView) findViewById(R.id.wait_tv);
    }

    /**
     * @param timeout_ms 超时。
     */
    public void show(long timeout_ms) {
        show();
        if (timeout_ms > 0) {
            if (mTimeoutHandler != null) {
                mTimeoutHandler.stop();
                mTimeoutHandler = null;
            }
            mTimeoutHandler = new TimeoutHandler(this, mTvTimeout);
            mTimeoutHandler.start((int) (timeout_ms / 1000));
        }
    }

    /** 一次性的 */
    private static class TimeoutHandler extends Handler {
        WeakReference<Dialog> mDialog;
        WeakReference<TextView> mTvTimeout;
        int timeout_s;

        TimeoutHandler(Dialog dialog, TextView tvTimeout) {
            mTvTimeout = new WeakReference<>(tvTimeout);
            mDialog = new WeakReference<>(dialog);
        }

        void start(int timeout_s) {
            this.timeout_s = timeout_s;
            sendEmptyMessage(0);
        }

        /** 只是停止计时，并没有关闭dialog */
        void stop() {
            mDialog.clear();
            mTvTimeout.clear();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mDialog.get() != null && mTvTimeout.get() != null) {
                if (timeout_s <= 0) {
                    mDialog.get().hide();
                    return;
                }
                mTvTimeout.get().setText(String.valueOf(timeout_s));
                timeout_s--;
                sendEmptyMessageDelayed(0, 1000);
            }
        }
    }
}
