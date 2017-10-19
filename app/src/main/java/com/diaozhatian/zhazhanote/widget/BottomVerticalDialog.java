package com.diaozhatian.zhazhanote.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.diaozhatian.zhazhanote.R;

/**
 * 描述：类似于QQ底部弹出框那样的垂直对话框。
 * <p>
 * Created by audienl@qq.com on 2017/5/3.
 */
public class BottomVerticalDialog extends DialogFragment {
    private static final String TAG = "BottomVerticalDialog";

    TextView mTvTitle;
    LinearLayout mLayoutBtnGroup;
    TextView mTvCancel;

    private String mTitle;
    private String[] mButtonTexts;
    private int[] mTextColors;

    public BottomVerticalDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_bottom_vertical, container);

        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mLayoutBtnGroup = (LinearLayout) view.findViewById(R.id.layout_btn_group);
        mTvCancel = (TextView) view.findViewById(R.id.tv_cancel);

        if (mTitle != null) {
            mTvTitle.setText(mTitle);
        }

        mTvCancel.setOnClickListener(v -> dismiss());

        // 添加按钮
        if (mButtonTexts != null) {
            for (int i = 0; i < mButtonTexts.length; i++) {
                final String text = mButtonTexts[i];
                // 添加横线
                View line = new View(getContext());
                line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
                line.setBackgroundColor(Color.parseColor("#AAAAAA"));
                mLayoutBtnGroup.addView(line);
                // 添加TextView
                TextView tv = new TextView(getContext());
                int height = (int) getResources().getDimension(R.dimen.new_114px);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
                p.gravity = Gravity.CENTER;
                tv.setLayoutParams(p);
                tv.setText(text);
                tv.setTextSize(20);
                tv.setGravity(Gravity.CENTER);

                if (mTextColors != null && mTextColors.length == mButtonTexts.length) {
                    tv.setTextColor(mTextColors[i]);
                }

                mLayoutBtnGroup.addView(tv);

                final int index = i;
                tv.setOnClickListener(v -> {
                    if (mCallback != null) {
                        mCallback.onItemClick(index, text);
                    }
                });
            }
        }

        // 设置动画
        Window win = getDialog().getWindow();
        if (win != null) {
            win.setWindowAnimations(R.style.BottomVerticalDialogAnimation);
        }

        return view;
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

    public BottomVerticalDialog setTitle(String title) {
        mTitle = title;
        if (mTvTitle != null && title != null) {
            mTvTitle.setText(title);
        }
        return this;
    }

    public BottomVerticalDialog setButtonTexts(String[] texts) {
        mButtonTexts = texts;
        return this;
    }

    /**
     * @param colors A color value in the form 0xAARRGGBB.必须是ARGB.
     */
    public BottomVerticalDialog setButtonTexts(String[] texts, @ColorInt int[] colors) {
        mButtonTexts = texts;
        mTextColors = colors;
        return this;
    }

    private Callback mCallback;

    public BottomVerticalDialog setCallback(Callback callback) {
        mCallback = callback;
        return this;
    }

    public interface Callback {
        void onItemClick(int index, String text);
    }
}
