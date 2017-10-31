package com.diaozhatian.zhazhanote.manager;

import android.content.Context;

import com.audienl.superlibrary.utils.LogUtils;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/31.
 */
public class GTReceiver extends GTIntentService {
    private static final String TAG = "GTReceiver";

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        LogUtils.info(TAG, "onReceiveServicePid", pid);
    }

    @Override
    public void onReceiveClientId(Context context, String cid) {
        LogUtils.info(TAG, "onReceiveClientId", cid);
        GTManager.setCID(cid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        LogUtils.info(TAG, "onReceiveMessageData", gtTransmitMessage.toString());
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {
        LogUtils.info(TAG, "onReceiveOnlineState", b);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
        LogUtils.info(TAG, "onReceiveCommandResult", gtCmdMessage.toString());
    }
}
