package com.audienl.superlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 描述：键盘相关操作
 * <p>
 * Created by audienl@qq.com on 2017/9/29.
 */
public class KeyboardUtils {

    public static void showKeyboard(EditText view) {
        if (view == null) return;
        view.requestFocus();
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void hideKeyboard(Activity activity) {
        if (activity == null) return;
        hideKeyboard(activity.getCurrentFocus());
    }

    /**
     * @param view Activity中随便一个View
     */
    public static void hideKeyboard(View view) {
        if (view == null) return;
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            view.clearFocus();
        }
    }

    public static void setOnKeyboardStatusChangedListener(View rootView, OnKeyboardStatusChangedListener listener) {
        if (rootView == null) return;
        KeyboardStatusChangedHelper helper = new KeyboardStatusChangedHelper();
        helper.setOnKeyboardStatusChangedListener(rootView, listener);
    }

    /**
     * 监听键盘弹起状态帮助类
     */
    static class KeyboardStatusChangedHelper {
        private int mOldVisibleHeight;

        /**
         * 获取某个View的可见高度
         */
        int getVisibleHeight(View view) {
            Rect rect = new Rect();
            view.getWindowVisibleDisplayFrame(rect);
            return rect.bottom - rect.top;
        }

        /**
         * 监听键盘的弹起状态
         */
        void setOnKeyboardStatusChangedListener(final View rootView, final OnKeyboardStatusChangedListener listener) {
            // TODO: 2017/9/29  仅仅实现了功能，但是具体细节没有验证，比如键盘高度不对等
            mOldVisibleHeight = getVisibleHeight(rootView);
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int visibleHeight = getVisibleHeight(rootView);// 可见高度
                    if (visibleHeight != mOldVisibleHeight) {
                        int rootViewHeight = rootView.getHeight();// 总高度
                        int differentHeight = rootViewHeight - visibleHeight;// 相差高度
                        if (differentHeight > (rootViewHeight / 4)) {
                            // 判断为键盘弹起
                            if (listener != null) listener.onChanged(true, differentHeight);
                        } else {
                            // 判断为键盘收起
                            if (listener != null) listener.onChanged(false, differentHeight);
                        }
//                rootView.requestLayout();
                        mOldVisibleHeight = visibleHeight;
                    }
                }
            });
        }
    }

    public interface OnKeyboardStatusChangedListener {
        /**
         * @param isShow true 为弹起，false 为收起
         */
        void onChanged(boolean isShow, int keyboardHeight);
    }
}
