package com.diaozhatian.zhazhanote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.audienl.superlibrary.base.SuperRecyclerViewAdapter;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.bean.Folder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/11/6.
 */
public class FolderAdapter extends SuperRecyclerViewAdapter<Folder> {
    public FolderAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_folder, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, Folder folder) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTvContent.setText(folder.name);
        holder.mBtnSelected.setVisibility(folder.isSelected ? View.VISIBLE : View.GONE);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_delete) ImageView mBtnDelete;
        @BindView(R.id.tvContent) TextView mTvContent;
        @BindView(R.id.top_view) RelativeLayout mTopView;
        @BindView(R.id.btn_select) ImageView mBtnSelected;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
