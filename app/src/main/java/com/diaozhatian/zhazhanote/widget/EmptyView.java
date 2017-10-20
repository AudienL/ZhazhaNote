package com.diaozhatian.zhazhanote.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.diaozhatian.zhazhanote.R;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/8/23.
 */
public class EmptyView extends FrameLayout {

    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.empty_view, this, true);
    }
}
