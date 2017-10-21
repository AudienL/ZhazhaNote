package com.diaozhatian.zhazhanote.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.activity.EditNoteActivity;
import com.diaozhatian.zhazhanote.adapter.NoteListAdapter;
import com.diaozhatian.zhazhanote.annotation.NoteType;
import com.diaozhatian.zhazhanote.base.BaseFragment;
import com.diaozhatian.zhazhanote.bean.Note;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.bean.event.OnAddNoteSuccessEvent;
import com.diaozhatian.zhazhanote.bean.event.RequestRefreshNoteListEvent;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.BottomVerticalDialog;
import com.diaozhatian.zhazhanote.widget.EmptyView;
import com.diaozhatian.zhazhanote.widget.NoteDetailDialog;
import com.diaozhatian.zhazhanote.widget.XRecyclerView2;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainFragment extends BaseFragment {
    private static final String TAG = "MainFragment";

    @BindView(R.id.recyclerView) XRecyclerView2 mRecyclerView;
    @BindView(R.id.emptyView) EmptyView mEmptyView;

    private NoteListAdapter mNoteListAdapter;
    private String mNoteType;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(@NoteType String noteType) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("note_type", noteType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNoteType = getArguments().getString("note_type");
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        mRecyclerView.setEmptyView(mEmptyView);
        mNoteListAdapter = new NoteListAdapter(mBaseActivity);
        mRecyclerView.setAdapter(mNoteListAdapter);
    }

    @Override
    public void initListeners() {
        mRecyclerView.setOnRefreshListener(this::request);
        mRecyclerView.setOnLoadMoreListener(this::request);

        mNoteListAdapter.setOnItemClickListener((view, i, note) -> {
//            updateNote(note);
            EditNoteActivity.start(mBaseActivity, false, note);
        });

        mNoteListAdapter.setOnItemLongClickListener((view, i, note) -> {
            note.selected = true;
            mNoteListAdapter.notifyDataSetChanged();

            BottomVerticalDialog dialog = new BottomVerticalDialog();
            dialog.setButtonTexts(new String[]{note.validStatus == 0 ? "取消完成" : "设为完成", note.top == 1 ? "取消置顶" : "置顶", "删除"});
            dialog.setCallback((index, text) -> {
                switch (index) {
                    case 0:
                        // 完成
                        toggleFinish(note);
                        break;
                    case 1:
                        // 置顶
                        toggleTopNote(note);
                        break;
                    case 2:
                        // 删除
                        deleteNote(note.id);
                        break;
                }
                dialog.dismiss();
            });
            dialog.setOnDismissListener(() -> {
                note.selected = false;
                mNoteListAdapter.notifyDataSetChanged();
            });
            dialog.show(mBaseActivity.getSupportFragmentManager(), "");
            return false;
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        mRecyclerView.refresh();
    }

    private void updateNote(Note note) {
        NoteDetailDialog dialog = new NoteDetailDialog();
        dialog.setContent(note.content);
        dialog.setOnDismissListener(() -> {
            User user = UserManager.checkUserLogin();
            if (user == null) return;
            String content = dialog.getContent();
            // 无变动 or 全部删除了
            if (TextUtils.isEmpty(content) || content.equals(note.content)) return;

            // TODO: 2017/10/20  不应该传userid

            Api.updateNote(String.valueOf(user.userId), content, mNoteType, "#FFFFFF").subscribe(result -> {
                ToastUtils.showToast(mBaseActivity, "更改计划成功");
                mRecyclerView.refresh();
            }, throwable -> {
                ToastUtils.showToast(mBaseActivity, throwable.getMessage());
            });
        });
        dialog.show(getFragmentManager(), "");
    }

    private void toggleFinish(Note note) {
        Api.updateNoteStatus(note.id, note.validStatus == 0 ? 1 : 0).subscribe(result -> {
            mRecyclerView.refresh();
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
        });
    }

    private void toggleTopNote(Note note) {
        Api.setNoteTopStatus(note.id, note.top == 1 ? 0 : 1).subscribe(result -> {
            mRecyclerView.refresh();
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
        });
    }

    private void deleteNote(int noteId) {
        Api.deleteNote(noteId).subscribe(result -> {
            mRecyclerView.refresh();
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
        });
    }

    private void request(int page, int pageSize) {
        User user = UserManager.checkUserLogin();
        if (user == null) return;

        Api.getNoteList(String.valueOf(user.userId), mNoteType, page, pageSize).subscribe(notes -> {
            // 排序
            List<Note> topList = new ArrayList<>();
            List<Note> normalList = new ArrayList<>();
            List<Note> finishList = new ArrayList<>();
            for (Note note : notes) {
                if (note.validStatus == 0) {
                    // 已完成
                    finishList.add(note);
                } else if (note.top == 1) {
                    // 置顶
                    topList.add(note);
                } else {
                    // 正常
                    normalList.add(note);
                }
            }
            topList.addAll(normalList);
            topList.addAll(finishList);

            mRecyclerView.handleOnNext(topList);
        }, throwable -> mRecyclerView.handleOnError());
    }

    @Subscribe
    public void onAddNoteSuccess(OnAddNoteSuccessEvent event) {
        if (mNoteType.equals(event.noteType)) {
            mRecyclerView.refresh();
        }
    }

    @Subscribe
    public void onRequestRefreshNoteList(RequestRefreshNoteListEvent event) {
        if (mNoteType.equals(event.noteType)) {
            mRecyclerView.refresh();
        }
    }
}
