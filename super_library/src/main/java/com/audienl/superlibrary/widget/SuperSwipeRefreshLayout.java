package com.audienl.superlibrary.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * 描述：下拉刷新，内部必须放一个上拉加载　LoadMoreListView。
 * <p>
 * Created by audienl@qq.com on 2017/6/14.
 */
public class SuperSwipeRefreshLayout extends SwipeRefreshLayout {
    private static final String TAG = "SuperSwipeRefreshLayout";
    private Context mContext;

    /** onFinishInflate 之后才有值 */
    private LoadMoreListView mLoadMoreListView;
    private boolean mLoadMoreEnableTmp;// 下拉刷新的时候保存下拉加载View的状态

    public SuperSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SuperSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // SwipeRefreshLayout 在构造方法里面添加了一个 CircleImageView ，所以这里 getChildCount() != 2
        if (getChildCount() != 2 || !(getChildAt(1) instanceof LoadMoreListView)) {
            throw new RuntimeException("SuperSwipeRefreshLayout 内部必须有且只有一个 LoadMoreListView！");
        }

        mLoadMoreListView = (LoadMoreListView) getChildAt(1);
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener listener) {
        super.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新的时候不允许上拉加载
                mLoadMoreEnableTmp = mLoadMoreListView.getLoadMoreEnable();
                mLoadMoreListView.setLoadMoreEnable(false);
                listener.onRefresh();
            }
        });
    }

    /**
     * 使用 setRefreshingSuccess 和 setRefreshingError 替代。
     */
    @Deprecated
    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
    }

    /**
     * 刷新成功
     */
    public void setRefreshingSuccess(int dataSize, int pageSize) {
        // 重置上拉加载View的状态
        mLoadMoreListView.reset();
        if (pageSize > 0 && dataSize < pageSize) {
            // 没有更多数据了
            mLoadMoreListView.setLoadingFinish(true);
        }
        super.setRefreshing(false);
    }

    /**
     * 刷新失败
     */
    public void setRefreshingError() {
        // 还原上拉加载View的状态
        mLoadMoreListView.setLoadMoreEnable(mLoadMoreEnableTmp);
        super.setRefreshing(false);
    }
}
