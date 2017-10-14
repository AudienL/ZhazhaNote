package com.diaozhatian.zhazhanote.http;

import com.audienl.superlibrary.utils.encrypt.Base64Utils;
import com.audienl.superlibrary.utils.encrypt.RSAUtils;
import com.diaozhatian.zhazhanote.Constants;

import java.security.PublicKey;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/15.
 */
public class ApiEncryptUtils {

    /**
     * 后台API要求的加密
     */
    public static String encrypt(String content) throws Exception {
        try {
            content = Base64Utils.encode(content.getBytes());
            PublicKey publicKey = RSAUtils.getPublicKey(Constants.API_RSA_MODULUS, Constants.API_RSA_PUBLIC_EXPONENT);
            byte[] bytes = RSAUtils.encryptData(content.getBytes(), publicKey);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("网络请求参数加密出错");
        }
    }
}
