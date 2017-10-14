package com.audienl.superlibrary.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/5/11.
 */
public class ToastUtils {
    private static Toast mToast = null;

    public static void showToast(Context context, int resId) {
        String text = context.getString(resId);
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToast(final Context context, final String text, final int duration) {
        if (TextUtils.isEmpty(text)) return;

//        if (Looper.myLooper() == Looper.getMainLooper()) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
//        } else {
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    if (mToast == null) {
//                        mToast = Toast.makeText(context, text, duration);
//                    } else {
//                        mToast.setText(text);
//                        mToast.setDuration(duration);
//                    }
//                    mToast.show();
//                }
//            });
//        }
    }
}
