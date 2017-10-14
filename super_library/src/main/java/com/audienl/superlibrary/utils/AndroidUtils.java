package com.audienl.superlibrary.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.audienl.superlibrary.utils.encrypt.MD5Utils;

import java.util.Arrays;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/6/27.
 */
public class AndroidUtils {
    private static final String TAG = "AndroidUtils";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void printBuildMessage() {
        LogUtils.info(TAG, "Build.UNKNOWN", Build.UNKNOWN);
        LogUtils.info(TAG, "Build.VERSION.SDK_INT", Build.VERSION.SDK_INT);
        LogUtils.info(TAG, "Build.ID", Build.ID);
        LogUtils.info(TAG, "Build.DISPLAY", Build.DISPLAY);
        LogUtils.info(TAG, "Build.PRODUCT", Build.PRODUCT);
        LogUtils.info(TAG, "Build.DEVICE", Build.DEVICE);
        LogUtils.info(TAG, "Build.BOARD", Build.BOARD);
        LogUtils.info(TAG, "Build.MANUFACTURER", Build.MANUFACTURER);
        LogUtils.info(TAG, "Build.BRAND", Build.BRAND);
        LogUtils.info(TAG, "Build.MODEL", Build.MODEL);
        LogUtils.info(TAG, "Build.BOOTLOADER", Build.BOOTLOADER);
        LogUtils.info(TAG, "Build.HARDWARE", Build.HARDWARE);
        LogUtils.info(TAG, "Build.SERIAL", Build.SERIAL);
        LogUtils.info(TAG, "Build.SUPPORTED_ABIS", Arrays.asList(Build.SUPPORTED_ABIS));
        LogUtils.info(TAG, "Build.SUPPORTED_32_BIT_ABIS", Arrays.asList(Build.SUPPORTED_32_BIT_ABIS));
        LogUtils.info(TAG, "Build.SUPPORTED_64_BIT_ABIS", Arrays.asList(Build.SUPPORTED_64_BIT_ABIS));
    }

    /**
     * 获取设备唯一标识。
     * <p>
     * 原理：则获取 ANDROID_ID + SERIAL + deviceId，然后在md5加密
     * <p>
     * Requires Permission:{@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     */
    public static String getDeviceUniqueId(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = manager.getDeviceId();
        String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        String SERIAL = android.os.Build.SERIAL;
        String result = (ANDROID_ID == null ? "" : ANDROID_ID) + (SERIAL == null ? "" : SERIAL) + (deviceId == null ? "" : deviceId);

        if (TextUtils.isEmpty(result)) {
            return "get_unique_id_error";
        } else {
            // ffa8c97108a82e6e05aac9b18ee9a80b
            return MD5Utils.md5(result);
        }
    }

    /**
     * 判断用户是否是第一次打开app<br>
     * @param currentVersionCode 当前版本号，可以通过 BuildConfig.VERSION_CODE 获取
     * @return 第一次安装，或者版本号不一致，都返回 true
     */
    public static boolean isFirstOpenApp(Context context, int currentVersionCode) {
        // 获取旧版本号
        int oldVersionCode = SPUtils.getInt(context, "current_version_code", -1);

        // 比较版本号
        if (oldVersionCode != currentVersionCode) {
            SPUtils.putInt(context, "current_version_code", currentVersionCode);
            return true;
        }
        return false;
    }
}
