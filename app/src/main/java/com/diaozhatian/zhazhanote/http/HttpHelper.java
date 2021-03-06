package com.diaozhatian.zhazhanote.http;

import com.audienl.superlibrary.utils.LogUtils;
import com.audienl.superlibrary.utils.NetworkUtils;
import com.audienl.superlibrary.utils.ToastUtils;
import com.diaozhatian.zhazhanote.base.App;
import com.diaozhatian.zhazhanote.bean.User;
import com.diaozhatian.zhazhanote.manager.UserManager;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.reactivex.ObservableEmitter;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/11.
 */
public class HttpHelper<T extends HttpResult> implements Callback.CommonCallback<String> {
    private static final String TAG = "HttpHelper";

    private Class<T> mClass;
    private int mTimeOut = 60000;
    private ObservableEmitter<T> mEmitter;
    private String mDesc;// 接口描述

    public HttpHelper(Class<T> clz, ObservableEmitter<T> emitter, String desc) {
        this.mClass = clz;
        mEmitter = emitter;
        mDesc = desc;
    }

    public void post(RequestParams params) {
        if (!NetworkUtils.isNetworkAvailable(App.instance)) {
            ToastUtils.showToast(App.instance, "当前网络不可用，请检查网络");
            return;
        }

        LogUtils.info(TAG, "POST", params.toString(), params.getBodyContent());
        params.setConnectTimeout(mTimeOut);
        params.setAsJsonContent(true);

        User user = UserManager.getLoginUser();
        if (user != null && user.token != null) {
            params.addHeader("token", user.token);
        }

        x.http().post(params, this);
    }

    public void get(RequestParams params) {
        if (!NetworkUtils.isNetworkAvailable(App.instance)) {
            ToastUtils.showToast(App.instance, "当前网络不可用，请检查网络");
            return;
        }

        LogUtils.info(TAG, "GET", params.toString());
        params.setConnectTimeout(mTimeOut);

        User user = UserManager.getLoginUser();
        if (user != null && user.token != null) {
            params.addHeader("token", user.token);
        }

        x.http().get(params, this);
    }

    @Override
    public void onSuccess(String result) {
        LogUtils.info(TAG, mDesc, result);
        try {
            T response = new Gson().fromJson(result, mClass);
            if (response == null) {
                mEmitter.onError(new Exception("服务器返回null"));
                return;
            }
            if (response.isSuccess()) {
                mEmitter.onNext(response);
            } else {
                mEmitter.onError(new Exception(response.message));
            }
        } catch (Exception e) {
            mEmitter.onError(e);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtils.info(TAG, mDesc, ex.getMessage());
        mEmitter.onError(ex);
    }

    @Override
    public void onCancelled(CancelledException cex) {
        LogUtils.info(TAG, mDesc, "cancel", cex.getMessage());
        mEmitter.onError(new Exception("已取消"));
    }

    @Override
    public void onFinished() {
        mEmitter.onComplete();
    }
}
