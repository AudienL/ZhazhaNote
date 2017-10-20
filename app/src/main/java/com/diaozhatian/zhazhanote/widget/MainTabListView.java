package com.diaozhatian.zhazhanote.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.annotation.NoteType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/20.
 */
public class MainTabListView extends FrameLayout {
    @BindView(R.id.tv1) TextView mTv1;
    @BindView(R.id.tv2) TextView mTv2;
    @BindView(R.id.tv3) TextView mTv3;
    @BindView(R.id.tv4) TextView mTv4;
    @BindView(R.id.tv5) TextView mTv5;

    private List<TextView> mTextViews;
    private String mCurrentNoteType;

    public MainTabListView(@NonNull Context context) {
        this(context, null);
    }

    public MainTabListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTabListView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.main_tab_list_view, this, true);
        ButterKnife.bind(this, view);

        mTextViews = new ArrayList<>();
        mTextViews.add(mTv1);
        mTextViews.add(mTv2);
        mTextViews.add(mTv3);
        mTextViews.add(mTv4);
        mTextViews.add(mTv5);

        mTv1.setText("日计划");
        mTv2.setText("月计划");
        mTv3.setText("周计划");
        mTv4.setText("季计划");
        mTv5.setText("年计划");

        setCurrentIndex(0);
    }

    @NoteType
    public String getCurrentNoteType() {
        return mCurrentNoteType;
    }

    public void setCurrentIndex(int index) {
        if (index < 0 || index > 4) return;
        for (TextView textView : mTextViews) {
            textView.setSelected(false);
        }
        mTextViews.get(index).setSelected(true);

        switch (index) {
            case 0:
                mCurrentNoteType = NoteType.DAY;
                break;
            case 1:
                mCurrentNoteType = NoteType.WEEK;
                break;
            case 2:
                mCurrentNoteType = NoteType.MONTH;
                break;
            case 3:
                mCurrentNoteType = NoteType.SEASON;
                break;
            case 4:
                mCurrentNoteType = NoteType.YEAR;
                break;
        }
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                setCurrentIndex(0);
                if (mOnTabChangedListener != null) mOnTabChangedListener.onChanged(0);
                break;
            case R.id.tv2:
                setCurrentIndex(1);
                if (mOnTabChangedListener != null) mOnTabChangedListener.onChanged(1);
                break;
            case R.id.tv3:
                setCurrentIndex(2);
                if (mOnTabChangedListener != null) mOnTabChangedListener.onChanged(2);
                break;
            case R.id.tv4:
                setCurrentIndex(3);
                if (mOnTabChangedListener != null) mOnTabChangedListener.onChanged(3);
                break;
            case R.id.tv5:
                setCurrentIndex(4);
                if (mOnTabChangedListener != null) mOnTabChangedListener.onChanged(4);
                break;
        }
    }

    private OnTabChangedListener mOnTabChangedListener;

    public void setOnTabChangedListener(OnTabChangedListener onTabChangedListener) {
        mOnTabChangedListener = onTabChangedListener;
    }

    public interface OnTabChangedListener {
        void onChanged(int currentIndex);
    }
}
