package com.audienl.superlibrary.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.audienl.superlibrary.R;

import java.util.List;

/**
 * 描述：仅支持String。
 * 通过 getSelectedItem() 获取选中值即可。
 * <p>
 * Created by audienl@qq.com on 2017/8/4.
 */
public class StringSpinner extends AppCompatSpinner {
    private static final String TAG = "MySpinner";
    private Context mContext;

    public StringSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        setBackgroundResource(R.drawable.string_spinner_view_bg);
    }

    public void setData(List<String> data) {
        if (data != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.string_spinner_view, android.R.id.text1, data);
            adapter.setDropDownViewResource(R.layout.string_spinner_drop_down_view);
            setAdapter(adapter);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        setDropDownVerticalOffset(b - t);
        setDropDownWidth(r - l);
    }
}
