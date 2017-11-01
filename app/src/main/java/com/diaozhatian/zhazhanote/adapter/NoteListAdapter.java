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
import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.activity.EditNoteActivity;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.bean.Note;
import com.diaozhatian.zhazhanote.bean.event.RequestRefreshNoteListEvent;
import com.diaozhatian.zhazhanote.http.Api;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/20.
 */
public class NoteListAdapter extends SuperRecyclerViewAdapter<Note> {
    private boolean showNoteType = false;

    private BaseActivity mBaseActivity;

    public NoteListAdapter(Context context) {
        this(context, false);
    }

    /**
     * @param showNoteType 是否在每个计划上显示计划类型
     */
    public NoteListAdapter(Context context, boolean showNoteType) {
        super(context);
        this.showNoteType = showNoteType;

        if (context instanceof BaseActivity) {
            mBaseActivity = (BaseActivity) context;
        } else {
            throw new RuntimeException("NoteListAdapter的context必须传入BaseActivity");
        }
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
        holder.mTvTime.setText(DateUtils.format("yyyy.MM.dd HH:mm", note.updateTime));
        holder.mBtnStar.setSelected(note.top == 1);

        // NoteType
        holder.mTvNoteType.setVisibility(showNoteType ? View.VISIBLE : View.GONE);
        holder.mTvNoteType.setText(note.type + "计划");

        if (note.finish == 1) {
            // 已完成
            holder.mTvContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mTvContent.setSelected(true);
            holder.mBtnSelect.setSelected(true);
        } else {
            holder.mTvContent.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
            holder.mTvContent.setSelected(false);
            holder.mBtnSelect.setSelected(false);
        }

        // 点击星星
        holder.mBtnStar.setOnClickListener(v -> {
            Api.setNoteTopStatus(note.id, note.top == 1 ? 0 : 1).subscribe(result -> {
                EventBus.getDefault().post(new RequestRefreshNoteListEvent(note.type));
            }, throwable -> {
                ToastUtils.showToast(mContext, throwable.getMessage());
            });
        });

        // 点击选择
        holder.mBtnSelect.setOnClickListener(v -> {
            Api.setFinished(note.id, holder.mBtnSelect.isSelected() ? 0 : 1).subscribe(result -> {
                EventBus.getDefault().post(new RequestRefreshNoteListEvent(note.type));
            }, throwable -> {
                ToastUtils.showToast(mContext, throwable.getMessage());
            });
        });

        holder.mTopView.setOnClickListener(v -> {
            EditNoteActivity.start(mBaseActivity, false, note);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvContent) TextView mTvContent;
        @BindView(R.id.tvTime) TextView mTvTime;
        @BindView(R.id.tv_note_type) TextView mTvNoteType;
        @BindView(R.id.btn_star) ImageView mBtnStar;
        @BindView(R.id.btn_select) ImageView mBtnSelect;

        @BindView(R.id.top_view) ViewGroup mTopView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
