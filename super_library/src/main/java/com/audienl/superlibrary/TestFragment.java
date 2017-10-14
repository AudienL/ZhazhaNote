package com.audienl.superlibrary;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.audienl.superlibrary.utils.LogUtils;


public class TestFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private TextView mTextView;

    private int mBackgroundColor = Color.CYAN;
    private String mText;
    private int mPosition;

    public TestFragment() {
        // Required empty public constructor
    }

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        mTextView = (TextView) view.findViewById(R.id.text_view);

        view.setBackgroundColor(mBackgroundColor);
        if (mText != null) mTextView.setText(mText);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.info(TAG, "on_create", mPosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.info(TAG, "on_destroy", mPosition);
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    public void setText(String text) {
        mText = text;
    }
}
