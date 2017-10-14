package com.audienl.superlibrary.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/9/22.
 */
public class IntentHelper {
    private static Map<String, Object> mIntentData = new HashMap<>();

    public static void put(String TAG, String key, Object value) {
        if (TAG == null || key == null) throw new RuntimeException("TAG 和 key 不能为null");
        mIntentData.put(generateKey(TAG, key), value);
    }

    public static Object get(String TAG, String key) {
        String ownerKey = generateKey(TAG, key);
        Object obj = mIntentData.get(ownerKey);
        mIntentData.remove(ownerKey);
        return obj;
    }

    private static String generateKey(String TAG, String key) {
        return TAG + "_" + key;
    }
}
