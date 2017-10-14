package com.audienl.superlibrary.base;

import android.os.Handler;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/9/14.
 */
public class SuperFragment extends LifeCycleFragment {
    protected boolean isVisibleToUser;// 当前是否是可见状态
    protected Handler mHandler = new Handler();

    private boolean isFirstVisible = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser && isFirstVisible) {
            isFirstVisible = false;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onFirstVisibleDelayed();
                }
            }, 500);
        }
    }

    /**
     * Fragment第一次可见之后500毫秒回调。
     */
    public void onFirstVisibleDelayed() {}
}
