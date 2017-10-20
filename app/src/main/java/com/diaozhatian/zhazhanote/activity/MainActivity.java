package com.diaozhatian.zhazhanote.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.audienl.superlibrary.base.SuperFragmentPagerAdapter;
import com.audienl.superlibrary.utils.KeyboardUtils;
import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.annotation.NoteType;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.bean.event.OnAddNoteSuccessEvent;
import com.diaozhatian.zhazhanote.fragment.MainFragment;
import com.diaozhatian.zhazhanote.http.Api;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.diaozhatian.zhazhanote.widget.MainTabListView;
import com.pgyersdk.update.PgyUpdateManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.tvTitle) TextView mTvTitle;
    @BindView(R.id.etAddNote) EditText mEtAddNote;
    @BindView(R.id.viewPager) ViewPager mViewPager;
    @BindView(R.id.mainTabListView) MainTabListView mMainTabListView;

    private SuperFragmentPagerAdapter mFragmentPagerAdapter;

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
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        mTvTitle.setText(getText(R.string.app_name));

        mFragmentPagerAdapter = new SuperFragmentPagerAdapter(getSupportFragmentManager());
        mFragmentPagerAdapter.setFragments(MainFragment.newInstance(NoteType.DAY), MainFragment.newInstance(NoteType.WEEK), MainFragment.newInstance(NoteType.MONTH), MainFragment.newInstance(NoteType.SEASON), MainFragment.newInstance(NoteType.YEAR));
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
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
        KeyboardUtils.showKeyboard(mEtAddNote);
    }

    private void hideEditNote() {
        mEtAddNote.setVisibility(View.GONE);
        KeyboardUtils.hideKeyboard(mBaseActivity);

        String content = mEtAddNote.getText().toString();
        mEtAddNote.setText("");
        if (TextUtils.isEmpty(content)) return;
        User user = UserManager.checkUserLogin();
        if (user == null) return;
        String noteType = mMainTabListView.getCurrentNoteType();
        Api.addNote(String.valueOf(user.userId), content, noteType, "#FFFFFF").subscribe(result -> {
            EventBus.getDefault().post(new OnAddNoteSuccessEvent(noteType));
        }, throwable -> {
            ToastUtils.showToast(mBaseActivity, throwable.getMessage());
        });
    }

    @OnClick({R.id.btnSetting, R.id.btnAddNote})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSetting:
                // 设置
                SettingActivity.start(mBaseActivity);
                break;
            case R.id.btnAddNote:
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
