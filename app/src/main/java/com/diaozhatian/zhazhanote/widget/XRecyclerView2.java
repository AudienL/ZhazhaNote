package com.diaozhatian.zhazhanote.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.audienl.superlibrary.base.SuperRecyclerViewAdapter;
import com.audienl.superlibrary.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.LoadingMoreFooter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/8/24.
 */
public class XRecyclerView2 extends XRecyclerView {
    private static final String TAG = "XRecyclerView2";

    private EmptyView mEmptyView;
    private int mStartPage = 1;// 起始下标
    private int mCurrentPage = mStartPage;// 当前请求下标
    private int mPageSize = 15;

    public XRecyclerView2(Context context) {
        this(context, null);
    }

    public XRecyclerView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFootView(new FooterView(context));

        super.setLoadingListener(new LoadingListener() {
            @Override
            public void onRefresh() {
                if (mOnRefreshListener != null) {
                    mCurrentPage = mStartPage;
                    mOnRefreshListener.onRefresh(mCurrentPage, mPageSize);
                }
            }

            @Override
            public void onLoadMore() {
                if (mOnLoadMoreListener != null) {
                    mCurrentPage++;
                    mOnLoadMoreListener.onLoadMore(mCurrentPage, mPageSize);
                }
            }
        });
    }

    /**
     * 用 {@link #setOnRefreshListener(OnRefreshListener)} 和 {@link #setOnLoadMoreListener(OnLoadMoreListener)} 替代
     */
    @Deprecated
    @Override
    public void setLoadingListener(LoadingListener listener) {
//        super.setLoadingListener(listener);
    }

    /**
     * 使用 {@link #setEmptyView(EmptyView)} 替代
     * 因为原来的EmptyView原理是当数据为空的时候，显示EmptyView，隐藏RecyclerView，之后就不能刷新了。
     */
    @Deprecated
    @Override
    public void setEmptyView(View emptyView) {
        super.setEmptyView(emptyView);
    }

    /**
     * 自己在界面上加入 {@link EmptyView}
     */
    public void setEmptyView(EmptyView emptyView) {
        mEmptyView = emptyView;
    }

    /** 设置page的起始下标，默认为1 */
    public void setStartPage(int startPage) {
        mStartPage = startPage;
    }

    /** 默认为15 */
    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public <M> void handleOnNext(List<M> data) {
        SuperRecyclerViewAdapter<M> adapter = (SuperRecyclerViewAdapter<M>) getAdapter();
        if (mCurrentPage == 1) {
            // 刷新
            adapter.setData(data);

            // 无数据
            if (mEmptyView != null) {
                if (data.size() == 0) {
//                    mEmptyView.setNoData();
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                }
            }

        } else {
            // 加载
            adapter.addAll(data);
        }
        adapter.notifyDataSetChanged();

        refreshComplete();
        loadMoreComplete();

        // 没有更多数据了，必须放 loadMoreComplete() 之后
        setNoMore(data.size() < mPageSize);
    }

    /**
     * errorText 默认为：加载数据失败
     */
    public void handleOnError() {
        handleOnError(null);
    }

    /**
     * @param errorText 传入null为默认值：加载数据失败
     */
    public void handleOnError(String errorText) {
        if (errorText == null) errorText = "加载数据失败";

        ToastUtils.showToast(getContext(), errorText);

        if (mEmptyView != null && getAdapter().getItemCount() == 0) {
//            mEmptyView.setError(errorText);
            mEmptyView.setVisibility(View.VISIBLE);
        }

        // 加载更多失败
        if (mCurrentPage != 1) mCurrentPage--;

        refreshComplete();
        loadMoreComplete();
    }

    public void addSimpleDividerHorizontal() {
        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) return;
                outRect.bottom = 2;
            }
        });
    }

    class FooterView extends LoadingMoreFooter {

        public FooterView(Context context) {
            super(context);
        }

        @Override
        public void setState(int state) {
            super.setState(state);

            // 没有更多数据，不显示文字
            if (STATE_NOMORE == state) {
                View view = this.getChildAt(getChildCount() - 1);
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    textView.setText("");
                }
            }
        }
    }

    /////////// START: Listeners ///////////

    private OnRefreshListener mOnRefreshListener;
    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    @FunctionalInterface
    public interface OnRefreshListener {
        /** page 和 pageSize 只供外层调用接口使用，不要修改值之后再请求接口，这样会导致 handleXXX 处理不正确 */
        void onRefresh(int page, int pageSize);
    }

    @FunctionalInterface
    public interface OnLoadMoreListener {
        /** page 和 pageSize 只供外层调用接口使用，不要修改值之后再请求接口，这样会导致 handleXXX 处理不正确 */
        void onLoadMore(int page, int pageSize);
    }

    /////////// END: Listeners /////////////
}
