package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.audienl.superlibrary.utils.KeyboardUtils;
import com.audienl.superlibrary.utils.ToastUtils;
import com.audienl.superlibrary.utils.UIUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.adapter.FolderAdapter;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.bean.Folder;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.fragment.MainFragment;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.ZhaButton;
import com.pgyersdk.update.PgyUpdateManager;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_FOLDER_NAME = 1;

    @BindView(R.id.etAddNote) EditText mEtAddNote;
    @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;
    @BindView(R.id.btn_left) ImageView mBtnLeft;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.btn_right) ImageView mBtnRight;

    private FolderAdapter mFolderAdapter;
    private PopupWindow mFolderPopupWindow;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 检查更新
        PgyUpdateManager.register(this, "com.diaozhatian.zhazhanote.pugongying_provider");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main2;
    }

    @Override
    public void init() {
        mFolderAdapter = new FolderAdapter(mBaseActivity);

        loadFolderList();
    }

    @Override
    public void initListeners() {
        mFolderAdapter.setOnItemClickListener((view, i, folder) -> {
            if (mFolderPopupWindow != null) mFolderPopupWindow.dismiss();
            setCurrentFragment(folder);
        });
    }

    @Override
    public void onBackPressed() {
        if (isEditNoteShow()) {
            hideEditNote();
            return;
        }
        super.onBackPressed();
    }

    private void setCurrentFragment(Folder folder) {
        MainFragment fragment = MainFragment.newInstance(folder);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        if (mFolderAdapter.getCurrentFolder() != null) mFolderAdapter.getCurrentFolder().isSelected = false;
        folder.isSelected = true;
        mFolderAdapter.notifyDataSetChanged();
        mTvTitle.setText(folder.name);
    }

    private boolean isEditNoteShow() {
        return mEtAddNote.getVisibility() == View.VISIBLE;
    }

    private void showEditNote() {
        mEtAddNote.setVisibility(View.VISIBLE);
        mBtnRight.setImageResource(R.mipmap.toolbar_save);
        KeyboardUtils.showKeyboard(mEtAddNote);
    }

    private void hideEditNote() {
        mEtAddNote.setVisibility(View.GONE);
        mBtnRight.setImageResource(R.mipmap.toolbar_add);
        KeyboardUtils.hideKeyboard(mBaseActivity);

        String content = mEtAddNote.getText().toString();
        mEtAddNote.setText("");
        if (TextUtils.isEmpty(content)) return;
        String userId = "";
        User user = UserManager.getLoginUser();
        if (user != null) userId = String.valueOf(user.userId);
//        String noteType = mMainTabListView.getCurrentNoteType();
//        Api.addNote(userId, content, noteType, "#FFFFFF").subscribe(result -> {
//            EventBus.getDefault().post(new OnAddNoteSuccessEvent(noteType));
//        }, throwable -> {
//            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
//        });
    }

    private void showPopupFolders() {
        if (mFolderPopupWindow == null) {
            mFolderPopupWindow = new PopupWindow(this);
            mFolderPopupWindow.setWidth(UIUtils.getScreenWidth(mBaseActivity) / 2);
            mFolderPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            View view = LayoutInflater.from(this).inflate(R.layout.folder_list_view, null);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
            recyclerView.setAdapter(mFolderAdapter);
            ZhaButton btnCreate = (ZhaButton) view.findViewById(R.id.btn_create_folder);
            mFolderPopupWindow.setContentView(view);
            mFolderPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            mFolderPopupWindow.setOutsideTouchable(false);
            mFolderPopupWindow.setFocusable(true);

            btnCreate.setOnClickListener(v -> {
                // 新建文件夹
                User user = UserManager.checkUserLogin();
                if (user == null) return;
                TextEditActivity.startActivityForResult(mBaseActivity, REQUEST_CODE_FOLDER_NAME, "文件夹名", "保存", "请输入文件夹名", "");
            });
        }

        int xoff = -UIUtils.getScreenWidth(mBaseActivity) / 4 + mTvTitle.getWidth() / 2;
        mFolderPopupWindow.showAsDropDown(mTvTitle, xoff, 20);
    }

    private void loadFolderList() {
        showWaitDialog();
        Api.getFolderList(1, 100).subscribe(folders -> {
            if (folders.size() <= 0) {
                ToastUtils.showToast(mBaseActivity, "没有文件夹");
                return;
            }

            Folder folderCurrent = folders.get(0);
            folderCurrent.isSelected = true;
            mTvTitle.setText(folderCurrent.name);

            setCurrentFragment(folderCurrent);

            mFolderAdapter.setData(folders);

            hideWaitDialog();
        }, throwable -> {
            hideWaitDialog();
            ToastUtils.showToast(mBaseActivity, "加载文件夹失败");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_FOLDER_NAME:
                // 新建文件夹
                if (resultCode == RESULT_OK) {
                    String folderName = TextEditActivity.getContent(data);
                    if (TextUtils.isEmpty(folderName)) return;
                    User user = UserManager.checkUserLogin();
                    if (user == null) return;
                    Api.createFolder(user.userId, folderName).subscribe(httpResult -> {
                        loadFolderList();
                    }, throwable -> {
                        ToastUtils.showToast(mBaseActivity, throwable.getMessage());
                    });
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick({R.id.btn_left, R.id.tv_title, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                // 设置
                SettingActivity.start(mBaseActivity);
                break;
            case R.id.tv_title:
                // 文件夹列表
                showPopupFolders();
                break;
            case R.id.btn_right:
                // 添加
                if (isEditNoteShow()) {
                    hideEditNote();
                } else {
                    showEditNote();
                }
                break;
        }
    }
}
