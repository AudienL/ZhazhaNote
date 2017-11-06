package com.diaozhatian.zhazhanote.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.Gravity;

import com.audienl.superlibrary.utils.UIUtils;
import com.diaozhatian.zhazhanote.R;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/11/6.
 */
public class ZhaButton extends AppCompatButton {
    public ZhaButton(Context context) {
        this(context, null);
    }

    public ZhaButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZhaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundResource(R.drawable.zha_button);
        setTextColor(getResources().getColor(R.color.white_weak));
        setGravity(Gravity.CENTER);
        int padding = UIUtils.dp2px(context, 5);
        setPadding(padding, padding, padding, padding);
    }
}
