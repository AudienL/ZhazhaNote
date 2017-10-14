package com.audienl.superlibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.audienl.superlibrary.utils.LogUtils;

/**
 * @author audienl@qq.com on 2016/7/14.
 */
public class LifeCycleFragment extends Fragment {

    /**
     * 如果需要在Fragment前加入标识，继承此方法。
     */
    protected String getLifeCycleTag() {
        return "";
    }

    private void log(String msg) {
        LogUtils.info("LifeCycle", this.getClass().getSimpleName() + ": " + getLifeCycleTag() + " -> " + msg);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
    }

    /**
     * 默认不会打印此方法的log。
     * 需要打印的话请在onCreateView中调用此方法。
     */
    protected final void onCreateViewLog() {
        log("onCreateView");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log("onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("onDetach");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        log(" -> setUserVisibleHint " + ": isVisibleToUser = " + isVisibleToUser);
    }
}
