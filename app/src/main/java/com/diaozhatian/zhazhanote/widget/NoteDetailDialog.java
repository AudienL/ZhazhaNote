package com.diaozhatian.zhazhanote.widget;

import android.text.TextUtils;
import android.widget.EditText;

import com.audienl.superlibrary.widget.SuperBottomDialog;
import com.diaozhatian.zhazhanote.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/20.
 */
public class NoteDetailDialog extends SuperBottomDialog {
    @BindView(R.id.et_content) EditText mEtContent;
    Unbinder unbinder;

    private String mContent = "";

    @Override
    public int getLayoutResId() {
        return R.layout.note_detail_dialog;
    }

    @Override
    public void init() {
        unbinder = ButterKnife.bind(this, mRootView);

        mEtContent.setText(mContent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
    }

    public void setContent(String content) {
        if (TextUtils.isEmpty(content)) return;
        mContent = content;
    }

    public String getContent() {
        return mEtContent.getText().toString();
    }
}
