package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.adapter.NoteListAdapter;
import com.diaozhatian.zhazhanote.base.App;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.widget.BottomVerticalDialog;
import com.diaozhatian.zhazhanote.widget.EmptyView;
import com.diaozhatian.zhazhanote.widget.Toolbar;
import com.diaozhatian.zhazhanote.widget.XRecyclerView2;

import butterknife.BindView;

public class FavorNoteListActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recyclerView) XRecyclerView2 mRecyclerView;
    @BindView(R.id.emptyView) EmptyView mEmptyView;

    private NoteListAdapter mNoteListAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, FavorNoteListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_favor_note_list;
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
        mToolbar.setOnLeftButtonClickListener(view -> finish());

        mRecyclerView.setOnRefreshListener(this::request);
        mRecyclerView.setOnLoadMoreListener(this::request);

        mNoteListAdapter.setOnItemClickListener((view, i, note) -> EditNoteActivity.start(mBaseActivity, false, note));

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
//        String userId = "";
//        User user = UserManager.getLoginUser();
//        if (user != null) userId = String.valueOf(user.userId);

        Api.getFavorNoteList(page, pageSize).subscribe(notes -> mRecyclerView.handleOnNext(notes), throwable -> {
            mRecyclerView.handleOnError();

            // TODO: 2017/10/23
            ToastUtils.showToast(App.instance, "等待后台给接口");

        });
    }
}
