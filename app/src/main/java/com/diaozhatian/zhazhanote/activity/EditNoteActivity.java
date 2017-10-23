package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.audienl.superlibrary.utils.IntentHelper;
import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.bean.Note;
import com.diaozhatian.zhazhanote.bean.event.RequestRefreshNoteListEvent;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class EditNoteActivity extends BaseActivity {
    private static final String TAG = "EditNoteActivity";
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.et_content) EditText mEtContent;

    private boolean isNewNote;
    private Note mOldNote;

    /**
     * @param oldNote 如果 isNewNote=true ，传如null即可。
     */
    public static void start(Context context, boolean isNewNote, Note oldNote) {
        Intent starter = new Intent(context, EditNoteActivity.class);
        IntentHelper.put(TAG, "is_new_note", isNewNote);
        IntentHelper.put(TAG, "old_note", oldNote);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_edit_note;
    }

    @Override
    public void init() {
        isNewNote = (boolean) IntentHelper.get(TAG, "is_new_note", true);
        mOldNote = (Note) IntentHelper.get(TAG, "old_note", null);

        mToolbar.setTitle(isNewNote ? "新建计划" : "更改计划");
        if (mOldNote != null) {
            mEtContent.setText(mOldNote.content);
        }

        mEtContent.setSelection(mEtContent.length());
    }

    @Override
    public void initListeners() {
        mToolbar.setOnLeftButtonClickListener(view -> onBackPressed());
        mToolbar.setOnRightButtonClickListener(view -> {
            String content = mEtContent.getText().toString();
            if (isNewNote) {
                ToastUtils.showToast(mBaseActivity, "未实现");
                return;
//                    if (TextUtils.isEmpty(content)) {
//                        ToastUtils.showToast(mBaseActivity, "请输入计划内容");
//                        return;
//                    };
//                    User user = UserManager.checkUserLogin();
//                    if (user == null) return;
//                    String noteType = mMainTabListView.getCurrentNoteType();
//                    Api.addNote(String.valueOf(user.userId), content, noteType, "#FFFFFF").subscribe(result -> {
//                        EventBus.getDefault().post(new OnAddNoteSuccessEvent(noteType));
//                    }, throwable -> {
//                        ToastUtils.showToast(mBaseActivity, throwable.getMessage());
//                    });
            } else {
                Api.updateNote(String.valueOf(mOldNote.id), content, mOldNote.type, "#FFFFFF").subscribe(result -> {
                    ToastUtils.showToast(mBaseActivity, "更改计划成功");
                    EventBus.getDefault().post(new RequestRefreshNoteListEvent(mOldNote.type));
                    finish();
                }, throwable -> {
                    ToastUtils.showToast(mBaseActivity, throwable.getMessage());
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        // 新建、有修改过
        // 更改、有修改过
        String content = mEtContent.getText().toString();
        if ((isNewNote && content.length() > 0) || (!isNewNote && !content.equals(mOldNote.content))) {
            new AlertDialog.Builder(mBaseActivity).setMessage("您有修改过的内容，确定不保存吗？").setNegativeButton("不保存", (dialog, which) -> finish()).setPositiveButton("继续修改", null).show();
            return;
        }
        super.onBackPressed();
    }
}
