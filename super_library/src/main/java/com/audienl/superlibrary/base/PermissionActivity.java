package com.audienl.superlibrary.base;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Hashtable;
import java.util.Map;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/6/27.
 */
public class PermissionActivity extends LifeCycleActivity {
    private static final String TAG = "PermissionActivity";

    // key:request_code value:runnable
    private Map<Integer, Runnable> mRunnableOnPermissionGranted = new Hashtable<>();
    private Map<Integer, Runnable> mRunnableOnPermissionDenied = new Hashtable<>();
    private int mRequestCode = 0;

    /**
     * 某个权限是否已经授权
     * @param permission android.Manifest.permission.xxx
     */
    public boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 在某个权限下运行某些代码
     * @param permission                android.Manifest.permission.xxx
     * @param actionOnPermissionGranted 在权限被授权的情况运行的代码
     * @param actionOnPermissionDenied  在权限被拒绝的情况下运行的代码
     */
    public void runOnPermissionGranted(String permission, Runnable actionOnPermissionGranted, Runnable actionOnPermissionDenied) {
        if (isPermissionGranted(permission)) {
            runOnUiThread(actionOnPermissionGranted);
        } else {
            mRunnableOnPermissionGranted.put(mRequestCode, actionOnPermissionGranted);
            mRunnableOnPermissionDenied.put(mRequestCode, actionOnPermissionDenied);
            ActivityCompat.requestPermissions(this, new String[]{permission}, mRequestCode);
            mRequestCode++;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mRunnableOnPermissionGranted.containsKey(requestCode)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                runOnUiThread(mRunnableOnPermissionGranted.get(requestCode));
            } else {
                runOnUiThread(mRunnableOnPermissionDenied.get(requestCode));
            }
            mRunnableOnPermissionGranted.remove(mRequestCode);
            mRunnableOnPermissionDenied.remove(mRequestCode);
            return;
        }
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
    }
}
