package com.audienl.superlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.audienl.superlibrary.R;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/5/10.
 */
public class SettingItem extends FrameLayout {
    private static final String TAG = "SettingItem";

    private TextView mTvName;
    private TextView mTvRight;// 右边文字
    private ImageView mIvRight;// 右边图片

    private Context mContext;
    private String mRightText;
    private String mRightHint;

    public SettingItem(Context context) {
        this(context, null);
    }

    public SettingItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SettingItem, defStyleAttr, 0);
        String name = arr.getString(R.styleable.SettingItem_setting_item_name);
        mRightText = arr.getString(R.styleable.SettingItem_setting_item_right_text);
        mRightHint = arr.getString(R.styleable.SettingItem_setting_item_right_hint);
        boolean isArrowShow = arr.getBoolean(R.styleable.SettingItem_setting_item_arrow_show, true);
        arr.recycle();

        View root = LayoutInflater.from(context).inflate(R.layout.setting_item, this);
        mTvName = (TextView) root.findViewById(R.id.tv_name);
        mTvRight = (TextView) root.findViewById(R.id.tv_right);
        mIvRight = (ImageView) root.findViewById(R.id.iv_right);

        if (name != null) mTvName.setText(name);
        if (!TextUtils.isEmpty(mRightHint)) mTvRight.setHint(mRightHint);
        if (!TextUtils.isEmpty(mRightText)) mTvRight.setText(mRightText);
        mIvRight.setVisibility(isArrowShow ? VISIBLE : GONE);
    }

    public void setRightText(String text) {
        if (text != null) {
            mRightText = text;
            mTvRight.setText(text);
        }
    }

    public String getRightText() {
        return mRightText;
    }
}
