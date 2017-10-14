package com.audienl.superlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.audienl.superlibrary.R;
import com.audienl.superlibrary.utils.UIUtils;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/5/5.
 */
public class Toolbar extends FrameLayout {
    private static final String TAG = "Toolbar";
    private Context mContext;

    private ViewGroup mRootView;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvRight;
    private View mStatusBarView;

    private boolean isShowBackButton;
    private String mTitle;
    private String mRightButtonText;
    private boolean mShowStatusBarView;

    public Toolbar(@NonNull Context context) {
        this(context, null);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.Toolbar, defStyleAttr, 0);
        isShowBackButton = arr.getBoolean(R.styleable.Toolbar_toolbar_show_back_button, true);
        mTitle = arr.getString(R.styleable.Toolbar_toolbar_title);
        mRightButtonText = arr.getString(R.styleable.Toolbar_toolbar_button_right_text);
        int titleTextColor = arr.getColor(R.styleable.Toolbar_toolbar_title_text_color, -1);
        int backgroundColor = arr.getColor(R.styleable.Toolbar_toolbar_background_color, -1);
        boolean isBtnBackBlack = arr.getBoolean(R.styleable.Toolbar_toolbar_btn_back_black, false);
        mShowStatusBarView = arr.getBoolean(R.styleable.Toolbar_toolbar_show_status_bar_view, true);
        arr.recycle();

        LayoutInflater.from(context).inflate(R.layout.toolbar, this);
        mRootView = (ViewGroup) findViewById(R.id.root_view);

        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRight = (TextView) findViewById(R.id.tv_right);
        mStatusBarView = findViewById(R.id.status_bar_view);

        if (backgroundColor != -1) mRootView.setBackgroundColor(backgroundColor);
        if (titleTextColor != -1) mTvTitle.setTextColor(titleTextColor);

        mIvBack.setImageResource(isBtnBackBlack ? R.drawable.btn_back_black : R.drawable.btn_back);
        mIvBack.setVisibility(isShowBackButton ? VISIBLE : GONE);

        mTvTitle.setText(mTitle == null ? "" : mTitle);
        if (TextUtils.isEmpty(mRightButtonText)) {
            mTvRight.setVisibility(GONE);
        } else {
            mTvRight.setVisibility(VISIBLE);
            mTvRight.setText(mRightButtonText);
        }

        // 返回按钮
        mIvBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBackButtonClickListener != null) {
                    mOnBackButtonClickListener.onClick(v);
                }
            }
        });
        // 右边按钮
        mTvRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRightButtonClickListener != null) {
                    mOnRightButtonClickListener.onClick(v);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams params = mStatusBarView.getLayoutParams();
        int width = UIUtils.getScreenWidth(mContext);
        int height;
        if (mShowStatusBarView && Build.VERSION.SDK_INT >= 19) {
            height = UIUtils.getStatusBarHeight(mContext) + UIUtils.dp2px(mContext, 48);
            params.height = UIUtils.getStatusBarHeight(mContext);
            mStatusBarView.setLayoutParams(params);
        } else {
            height = UIUtils.dp2px(mContext, 48);
            mStatusBarView.setVisibility(GONE);
        }
        measureChildren(MeasureSpec.makeMeasureSpec(width, MeasureSpec.getMode(widthMeasureSpec)), MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec)));
        setMeasuredDimension(width, height);
    }

    public void hideBackButton() {
        mIvBack.setVisibility(INVISIBLE);
    }

    public void setTitle(String title) {
        if (title == null) return;
        mTitle = title;
        mTvTitle.setText(title);
    }

    public void setRightText(String text) {
        if (TextUtils.isEmpty(text)) return;
        mTvRight.setText(text);
    }

//    private OnButtonClickListener mOnButtonClickListener;
//
//    public void setOnButtonClickListener(OnButtonClickListener listener) {
//        mOnButtonClickListener = listener;
//    }
//
//    public interface OnButtonClickListener {
//        void onBackButtonClick();
//
//        void onRightButtonClick();
//    }

//    public static abstract class SimpleOnButtonClickListener implements OnButtonClickListener {
//
//        @Override
//        public abstract void onBackButtonClick();
//
//        @Override
//        public void onRightButtonClick() {
//        }
//    }

    /////////// START: Listener ///////////

    private OnBackButtonClickListener mOnBackButtonClickListener;
    private OnRightButtonClickListener mOnRightButtonClickListener;

    public Toolbar setOnBackButtonClickListener(OnBackButtonClickListener listener) {
        mOnBackButtonClickListener = listener;
        return this;
    }

    public Toolbar setOnRightButtonClickListener(OnRightButtonClickListener listener) {
        mOnRightButtonClickListener = listener;
        return this;
    }

    @FunctionalInterface
    public interface OnBackButtonClickListener {
        void onClick(View view);
    }

    public interface OnRightButtonClickListener {
        void onClick(View view);
    }

    /////////// END: Listener /////////////
}