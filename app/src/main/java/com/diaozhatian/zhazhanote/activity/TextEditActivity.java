package com.diaozhatian.zhazhanote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.audienl.superlibrary.utils.IntentHelper;
import com.audienl.superlibrary.utils.KeyboardUtils;
import com.diaozhatian.zhazhanote.R;
import com.diaozhatian.zhazhanote.base.BaseActivity;
import com.diaozhatian.zhazhanote.widget.Toolbar;

import butterknife.BindView;

public class TextEditActivity extends BaseActivity {
    private static final String TAG = "TextEditActivity";

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.et_content) EditText mEtContent;

    public static void startActivityForResult(Activity activity, int requestCode, String title, String rightText, String hint, String content) {
        startActivityForResult(activity, requestCode, title, rightText, hint, content, null);
    }

    /**
     * @param inputType EditText的inputType。传入null则为默认。
     *                  number:  {@link android.text.InputType#TYPE_CLASS_NUMBER}
     */
    public static void startActivityForResult(Activity activity, int requestCode, String title, String rightText, String hint, String content, Integer inputType) {
        Intent starter = new Intent(activity, TextEditActivity.class);
        IntentHelper.put(TAG, "title", title);
        IntentHelper.put(TAG, "right_text", rightText);
        IntentHelper.put(TAG, "hint", hint);
        IntentHelper.put(TAG, "content", content);
        IntentHelper.put(TAG, "input_type", inputType);
        activity.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_text_edit;
    }

    @Override
    public void init() {
        String title = (String) IntentHelper.get(TAG, "title");
        String rightText = (String) IntentHelper.get(TAG, "right_text");
        String hint = (String) IntentHelper.get(TAG, "hint");
        String content = (String) IntentHelper.get(TAG, "content");
        Integer inputType = (Integer) IntentHelper.get(TAG, "input_type");

        if (title != null) mToolbar.setTitle(title);
        if (rightText != null) mToolbar.setRightText(rightText);
        if (hint != null) mEtContent.setHint(hint);
        if (content != null) {
            mEtContent.setText(content);
            mEtContent.setSelection(content.length());
        }
        if (inputType != null) mEtContent.setInputType(inputType);

        KeyboardUtils.showKeyboard(mEtContent);
    }

    @Override
    public void initListeners() {
        mToolbar.setOnLeftButtonClickListener(v -> finish());

        mToolbar.setOnRightButtonClickListener(v -> {
            String content = mEtContent.getText().toString().trim();

            Intent intent = new Intent();
            intent.putExtra("content", content);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    /**
     * 在原 Activity 上的 onActivityResult 中调用获取数据。
     */
    public static String getContent(Intent data) {
        if (data == null) return null;
        return data.getStringExtra("content");
    }
}
