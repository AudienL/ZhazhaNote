package com.audienl.superlibrary.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.audienl.superlibrary.R;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/9/11.
 */
public abstract class SuperBottomDialog extends DialogFragment {

    protected View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(getLayoutResId(), container);

        init();

        // 设置动画
        Window win = getDialog().getWindow();
        if (win != null) {
            win.setWindowAnimations(R.style.BottomDialogAnimation);
        }

        return mRootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);

            // 设置宽度占满屏幕必须加上这句
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    /**
     * 返回布局文件的ResId，如：R.layout.activity_main。
     */
    @LayoutRes
    public abstract int getLayoutResId();

    public abstract void init();
}
