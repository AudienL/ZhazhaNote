package com.audienl.superlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 描述：支持上拉加载的 ListView 。
 * <p>
 * Created by audienl@qq.com on 2017/6/14.
 */
public class LoadMoreListView extends ListView {
    private static final String TAG = "LoadMoreListView";

    private Context mContext;
    /** 加载状态显示View */
    private LoadMoreView mLoadingView;

    /** 是否允许上拉加载 */
    private boolean isLoadMoreEnable = true;
    /** 正在加载中 */
    private boolean isLoading = false;

    private boolean isScrolling = false;

    public LoadMoreListView(Context context) {
        this(context, null);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mLoadingView = new LoadMoreView(mContext);
        super.addFooterView(mLoadingView);// 必须注意这里调用的是super

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
//                        LogUtils.info(TAG, "SCROLL_STATE_TOUCH_SCROLL");
                        isScrolling = true;
                        break;
                    case SCROLL_STATE_FLING:
//                        LogUtils.info(TAG, "SCROLL_STATE_FLING");
                        isScrolling = true;
                        break;
                    case SCROLL_STATE_IDLE:
//                        LogUtils.info(TAG, "SCROLL_STATE_IDLE");
                        isScrolling = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 1、滑动状态(包括用户手动滑动和抬手后的自动滚动)
                // 2、最后一个可见的View == 最后一个View
                // 3、最后一个View的bottom(相对于父View) == ListView的高度
                int last = getLastVisiblePosition();
                if (isScrolling
                        && (last > 0 && last == totalItemCount - 1)
                        && (mLoadingView != null && mLoadingView.getBottom() == getHeight())) {
                    load();
                }
            }
        });
    }

    @Override
    public void addFooterView(View v) {
        removeFooterView(mLoadingView);
        super.addFooterView(v);
        super.addFooterView(mLoadingView);
    }

    /**
     * 加载
     */
    private void load() {
        if (!isLoadMoreEnable) return;// 不允许上拉加载
        if (isLoading) return;// 正在加载中
        isLoading = true;
        mLoadingView.setOnClickListener(null);
        mLoadingView.onLoading();
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    /**
     * 重置
     */
    public void reset() {
        isLoading = false;
        isLoadMoreEnable = true;
        mLoadingView.reset();
    }

//    /**
//     * 设置加载完成
//     * @param dataSize 此次加载的数据数
//     * @param pageSize 每次加载的数据数
//     */
//    public void setLoadingFinish(int dataSize, int pageSize) {
//        isLoading = false;
//        mLoadingView.onLoadingFinish();
//        if (pageSize > 0 && dataSize < pageSize) {
//            // 没有更多数据了
//            isLoadMoreEnable = false;
//            mLoadingView.onNoMoreData();
//        }
//    }

    /**
     * 设置加载完成
     * @param noMoreData 没有更多数据。
     */
    public void setLoadingFinish(boolean noMoreData) {
        isLoading = false;
        mLoadingView.onLoadingFinish();
        if (noMoreData) {
            isLoadMoreEnable = false;
            mLoadingView.onNoMoreData();
        }
    }

    /**
     * 设置加载出错
     */
    public void setLoadingError() {
        isLoading = false;
        isLoadMoreEnable = false;
        mLoadingView.onLoadingError();

        mLoadingView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoadMoreEnable = true;
                load();
            }
        });
    }

    /**
     * 设置是否允许上拉加载。
     * 默认是允许上拉加载的，这里并没有移除加载状态View。
     */
    public void setLoadMoreEnable(boolean enable) {
        isLoadMoreEnable = enable;
    }

    public boolean getLoadMoreEnable() {
        return isLoadMoreEnable;
    }

    /**
     * 默认是显示加载状态View的。
     */
//    public void removeLoadingView() {
//        removeFooterView(mLoadingView);
//    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreListView setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
        return this;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    /**
     * 默认的LoadMoreView
     */
    private static class LoadMoreView extends LinearLayout implements ILoadMoreView {
        private TextView mTvText;

        public LoadMoreView(Context context) {
            super(context);
            mTvText = new TextView(context);
            float scale = context.getResources().getDisplayMetrics().density;
            int height = (int) (38 * scale + 0.5f);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
            mTvText.setLayoutParams(params);
            mTvText.setGravity(Gravity.CENTER);
            reset();
            this.addView(mTvText);
        }

        @Override
        public void reset() {
            mTvText.setText("上拉加载更多");
        }

        @Override
        public void onLoading() {
            mTvText.setText("正在加载..");
        }

        @Override
        public void onNoMoreData() {
            mTvText.setText("没有更多数据了");
        }

        @Override
        public void onLoadingFinish() {
            mTvText.setText("加载完成！");
        }

        @Override
        public void onLoadingError() {
            mTvText.setText("加载出错，点击重试！");
        }
    }

    public interface ILoadMoreView {
        void reset();

        void onLoading();

        void onNoMoreData();

        void onLoadingFinish();

        void onLoadingError();
    }
}
