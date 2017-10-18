package com.audienl.superlibrary.base;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.audienl.superlibrary.utils.ToastUtils;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/9/20.
 */
public abstract class SuperApplication extends Application {
    /** 用浏览器打开URL */
    public void startUrlWithBroswer(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("https") || url.startsWith("http") || url.startsWith("www")) {
                if (url.startsWith("www")) {
                    url = "http://" + url;
                }
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                ToastUtils.showToast(this, "无效链接");
            }
        }
    }
}
