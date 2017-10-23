package com.diaozhatian.zhazhanote.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.diaozhatian.zhazhanote.R;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/19.
 */
public class Toolbar extends com.audienl.superlibrary.widget.Toolbar {
    public Toolbar(@NonNull Context context) {
        this(context, null);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(R.color.toolbar));
    }
}
