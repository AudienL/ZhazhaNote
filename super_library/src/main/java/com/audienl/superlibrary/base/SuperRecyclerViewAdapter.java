package com.audienl.superlibrary.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/8/10.
 */
public abstract class SuperRecyclerViewAdapter<M> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected Context mContext;
    protected List<M> mData = new ArrayList<>();

    public SuperRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final M datum = mData.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) mOnItemClickListener.onItemClick(v, position, datum);
            }
        });

        // if mOnItemLongClickListener == null, return false
        // else return mOnItemLongClickListener.onItemLongClick()
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mOnItemLongClickListener != null && mOnItemLongClickListener.onItemLongClick(v, position, datum);
            }
        });

        onBindViewHolder(holder, position, datum);
    }

    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, M datum);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public boolean add(M datum) {
        return mData.add(datum);
    }

    public void add(int index, M datum) {
        mData.add(index, datum);
    }

    public boolean addAll(Collection<? extends M> data) {
        return mData.addAll(data);
    }

    public boolean addAll(int index, Collection<? extends M> data) {
        return mData.addAll(index, data);
    }

    public void setData(List<M> data) {
        if (data != null) {
            mData = data;
        }
    }

    public List<M> getData() {
        return mData;
    }

    public boolean remove(M datum) {
        return mData.remove(datum);
    }

    public void remove(int position) {
        mData.remove(position);
    }

    public void clear() {
        mData.clear();
    }

    /////////// START: Listeners ///////////

    private OnItemClickListener<M> mOnItemClickListener;
    private OnItemLongClickListener<M> mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener<M> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<M> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

//    @FunctionalInterface
    public interface OnItemClickListener<M> {
        void onItemClick(View view, int position, M datum);
    }

//    @FunctionalInterface
    public interface OnItemLongClickListener<M> {
        /**
         * @return true if the callback consumed the long click, false otherwise
         */
        boolean onItemLongClick(View view, int position, M datum);
    }

    /////////// END: Listeners /////////////
}
