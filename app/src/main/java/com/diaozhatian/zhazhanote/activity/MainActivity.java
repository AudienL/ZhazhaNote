package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.audienl.superlibrary.base.SuperFragmentPagerAdapter;
import com.audienl.superlibrary.utils.KeyboardUtils;
import com.audienl.superlibrary.utils.ToastUtils;
import com.audienl.superlibrary.utils.UIUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.adapter.FolderAdapter;
import com.diaozhatian.zhazhanote.annotation.NoteType;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.bean.Folder;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.bean.event.OnAddNoteSuccessEvent;
import com.diaozhatian.zhazhanote.fragment.MainFragment2;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.MainTabListView;
import com.diaozhatian.zhazhanote.widget.ZhaButton;
import com.pgyersdk.update.PgyUpdateManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.etAddNote) EditText mEtAddNote;
    @BindView(R.id.viewPager) ViewPager mViewPager;
    @BindView(R.id.mainTabListView) MainTabListView mMainTabListView;
    @BindView(R.id.btn_left) ImageView mBtnLeft;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.btn_right) ImageView mBtnRight;

    private SuperFragmentPagerAdapter mFragmentPagerAdapter;
    private FolderAdapter mFolderAdapter;

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
        mFragmentPagerAdapter = new SuperFragmentPagerAdapter(getSupportFragmentManager());
        mFragmentPagerAdapter.setFragments(MainFragment2.newInstance(NoteType.DAY), MainFragment2.newInstance(NoteType.WEEK), MainFragment2.newInstance(NoteType.MONTH), MainFragment2.newInstance(NoteType.SEASON), MainFragment2.newInstance(NoteType.YEAR));
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);

        Api.getFolderList(1, 100).subscribe(folders -> {
            if (folders.size() <= 0) {
                ToastUtils.showToast(mBaseActivity, "没有文件夹");
                return;
            }
            Folder folderCurrent = folders.get(0);
            folderCurrent.isSelected = true;
            mTvTitle.setText(folderCurrent.name);

            mFolderAdapter = new FolderAdapter(mBaseActivity);
            mFolderAdapter.setData(folders);
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, "加载文件夹失败");
        });
    }

    @Override
    public void initListeners() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mMainTabListView.setCurrentIndex(position);
            }
        });
        mMainTabListView.setOnTabChangedListener(currentIndex -> mViewPager.setCurrentItem(currentIndex));
    }

    @Override
    public void onBackPressed() {
        if (isEditNoteShow()) {
            hideEditNote();
            return;
        }
        super.onBackPressed();
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
        String noteType = mMainTabListView.getCurrentNoteType();
        Api.addNote(userId, content, noteType, "#FFFFFF").subscribe(result -> {
            EventBus.getDefault().post(new OnAddNoteSuccessEvent(noteType));
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
        });
    }

    private void showFolders() {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(UIUtils.getScreenWidth(mBaseActivity) / 2);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(this).inflate(R.layout.folder_list_view, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        recyclerView.setAdapter(mFolderAdapter);
        ZhaButton btnCreate = (ZhaButton) view.findViewById(R.id.btn_create_folder);
        btnCreate.setOnClickListener(v -> {
            // 新建文件夹
        });
        popupWindow.setContentView(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);

        int xoff = -UIUtils.getScreenWidth(mBaseActivity) / 4 + mTvTitle.getWidth() / 2;
        popupWindow.showAsDropDown(mTvTitle, xoff, 20);
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
                showFolders();
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
