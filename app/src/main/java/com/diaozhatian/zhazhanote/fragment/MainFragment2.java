package com.diaozhatian.zhazhanote.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.activity.EditNoteActivity;
import com.diaozhatian.zhazhanote.adapter.NoteListAdapter;
import com.diaozhatian.zhazhanote.annotation.NoteType;
import com.diaozhatian.zhazhanote.base.BaseFragment;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.bean.event.OnAddNoteSuccessEvent;
import com.diaozhatian.zhazhanote.bean.event.RequestRefreshNoteListEvent;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.BottomVerticalDialog;
import com.diaozhatian.zhazhanote.widget.EmptyView;
import com.diaozhatian.zhazhanote.widget.XRecyclerView2;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

public class MainFragment2 extends BaseFragment {
    @BindView(R.id.recyclerView) XRecyclerView2 mRecyclerView;
    @BindView(R.id.emptyView) EmptyView mEmptyView;

    private NoteListAdapter mNoteListAdapter;
    private String mNoteType;

    public MainFragment2() {
        // Required empty public constructor
    }

    public static MainFragment2 newInstance(@NoteType String noteType) {
        MainFragment2 fragment = new MainFragment2();
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
        return R.layout.fragment_main_fragment2;
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
            dialog.setButtonTexts(new String[]{"删除"});
            dialog.setCallback((index, text) -> {
                switch (index) {
                    case 0:
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

    private void deleteNote(int noteId) {
        Api.deleteNote(noteId).subscribe(result -> {
            mRecyclerView.refresh();
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
        });
    }

    private void request(int page, int pageSize) {
        String userId = "";
        User user = UserManager.getLoginUser();
        if (user != null) userId = String.valueOf(user.userId);

        Api.getMainNoteList(mNoteType, page, pageSize).subscribe(notes -> {
            // 排序
//            List<Note> topList = new ArrayList<>();
//            List<Note> normalList = new ArrayList<>();
//            List<Note> finishList = new ArrayList<>();
//            for (Note note : notes) {
//                if (note.validStatus == 0) {
//                    // 已完成
//                    finishList.add(note);
//                } else if (note.top == 1) {
//                    // 置顶
//                    topList.add(note);
//                } else {
//                    // 正常
//                    normalList.add(note);
//                }
//            }
//            topList.addAll(normalList);
//            topList.addAll(finishList);

//            mRecyclerView.handleOnNext(topList);
            mRecyclerView.handleOnNext(notes);
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
