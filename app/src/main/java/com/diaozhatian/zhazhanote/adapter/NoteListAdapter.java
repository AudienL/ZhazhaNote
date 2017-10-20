package com.diaozhatian.zhazhanote.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.audienl.superlibrary.base.SuperRecyclerViewAdapter;
import com.audienl.superlibrary.utils.DateUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.bean.Note;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/20.
 */
public class NoteListAdapter extends SuperRecyclerViewAdapter<Note> {
    public NoteListAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i, Note note) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTvContent.setText(note.content);
        holder.mTvTime.setText(DateUtils.format("MM月dd日 HH:mm", note.updateTime));
        holder.mIvIron.setImageResource(note.selected ? R.mipmap.item_note_left_iron_selected : R.mipmap.item_note_left_iron);
        holder.mIvStar.setVisibility(note.top == 1 && note.validStatus == 1 ? View.VISIBLE : View.GONE);

        if (note.validStatus == 0) {
            // 已完成
            holder.mTvContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mTvContent.setSelected(true);
        } else {
            holder.mTvContent.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
            holder.mTvContent.setSelected(false);
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        @ViewInject(R.id.tvTime) TextView mTvTime;
        @ViewInject(R.id.ivStar) ImageView mIvStar;
        @ViewInject(R.id.tvContent) TextView mTvContent;
        @ViewInject(R.id.ivIron) ImageView mIvIron;

        ViewHolder(View view) {
            super(view);
            x.view().inject(this, view);
        }
    }
}
