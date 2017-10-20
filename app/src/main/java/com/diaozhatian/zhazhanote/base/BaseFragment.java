package com.diaozhatian.zhazhanote.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.audienl.superlibrary.base.SuperFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/13.
 */
public abstract class BaseFragment extends SuperFragment {
    private boolean injected = false;
    protected BaseActivity mBaseActivity;
    protected View mRootView;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = LayoutInflater.from(mBaseActivity).inflate(getLayoutResId(), null);
        unbinder = ButterKnife.bind(this, mRootView);
        init();
        initListeners();
        loadData();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (unbinder == null) {
            mRootView = view;
            unbinder = ButterKnife.bind(this, view);
            init();
            initListeners();
            loadData();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @LayoutRes
    public abstract int getLayoutResId();

    public abstract void init();

    public abstract void initListeners();

    public void loadData() {}
}
