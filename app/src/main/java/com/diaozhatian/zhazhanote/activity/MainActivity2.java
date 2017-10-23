package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.audienl.superlibrary.base.SuperFragmentPagerAdapter;
import com.audienl.superlibrary.utils.KeyboardUtils;
import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.annotation.NoteType;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.bean.event.OnAddNoteSuccessEvent;
import com.diaozhatian.zhazhanote.fragment.MainFragment2;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.MainTabListView;
import com.diaozhatian.zhazhanote.widget.Toolbar;
import com.pgyersdk.update.PgyUpdateManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class MainActivity2 extends BaseActivity {
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.etAddNote) EditText mEtAddNote;
    @BindView(R.id.viewPager) ViewPager mViewPager;
    @BindView(R.id.mainTabListView) MainTabListView mMainTabListView;

    private SuperFragmentPagerAdapter mFragmentPagerAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity2.class);
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
        mToolbar.setLeftIcon(R.mipmap.toolbar_setting);

        mFragmentPagerAdapter = new SuperFragmentPagerAdapter(getSupportFragmentManager());
        mFragmentPagerAdapter.setFragments(MainFragment2.newInstance(NoteType.DAY), MainFragment2.newInstance(NoteType.WEEK), MainFragment2.newInstance(NoteType.MONTH), MainFragment2.newInstance(NoteType.SEASON), MainFragment2.newInstance(NoteType.YEAR));
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
    }

    @Override
    public void initListeners() {
        // 设置
        mToolbar.setOnLeftButtonClickListener(view -> {
            SettingActivity.start(mBaseActivity);
        });
        // 添加
        mToolbar.setOnRightButtonClickListener(view -> {
            if (isEditNoteShow()) {
                hideEditNote();
            } else {
                showEditNote();
            }
        });

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
        mToolbar.setRightIcon(R.mipmap.toolbar_save);
        KeyboardUtils.showKeyboard(mEtAddNote);
    }

    private void hideEditNote() {
        mEtAddNote.setVisibility(View.GONE);
        mToolbar.setRightIcon(R.mipmap.toolbar_add);
        KeyboardUtils.hideKeyboard(mBaseActivity);

        String content = mEtAddNote.getText().toString();
        mEtAddNote.setText("");
        if (TextUtils.isEmpty(content)) return;
//        User user = UserManager.checkUserLogin();
//        if (user == null) return;
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
}
