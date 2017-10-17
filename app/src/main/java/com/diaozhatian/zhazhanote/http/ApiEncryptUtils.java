package com.diaozhatian.zhazhanote.http;

import com.audienl.superlibrary.utils.encrypt.Base64Utils;
import com.diaozhatian.zhazhanote.utils.RSAUtil;

import java.security.interfaces.RSAPublicKey;

/**
 * 描述：
 * <p>
 * Created by audienl@qq.com on 2017/10/15.
 */
public class ApiEncryptUtils {
    private static final String TAG = "ApiEncryptUtils";

    /**
     * 后台API要求的加密
     */
    public static String encrypt(String content) throws Exception {
        try {
            String modulus = "107920443544455768016677584168539218636510423190166749858207053111985549971685450681854223707183979763669480082097213075685642473312627579352159815373238385671496948488830337447224954167005534404920894925171926220403339853330972872444943790512686025398908275326951007383446583337775901191019706911982355423473";
            String publicExponent = "65537";
            String privateExponent = "63281424614643494653598774463475496787806262916748066440347267101644760972609368513251689318146591365763682348519367705044838100720676339272840525885807667731265402232660069105336272750663420074619814393519147990378183663405760952781287728971804665596000936871061241979644464544636949410411177702722256575153";

            System.out.println("=== Base64 加密 ===");
            String base64Encoded = Base64Utils.encode(content.getBytes());
            System.out.println(base64Encoded);
            System.out.println("=== RSA 加密 ===");
            RSAPublicKey publicKey = RSAUtil.getPublicKey(modulus, publicExponent);
            String rsaEncoded = RSAUtil.encryptByPublicKey(base64Encoded, publicKey);
//            PublicKey publicKey = RSAUtils.getPublicKey(modulus, publicExponent);
//            String rsaEncoded = new String(RSAUtils.encryptData(base64Encoded.getBytes(), publicKey));
            System.out.println(rsaEncoded);

//            System.out.println("=== RSA 解密 ===");
////            RSAPrivateKey privateKey = RSAUtil.getPrivateKey(modulus, privateExponent);
////            String rsaDecoded = RSAUtil.decryptByPrivateKey(rsaEncoded, privateKey);
//            PrivateKey privateKey = RSAUtils.getPrivateKey(modulus, privateExponent);
//            String rsaDecoded = new String(RSAUtils.decryptData(rsaEncoded.getBytes(), privateKey));
//            System.out.println(rsaDecoded);
//            System.out.println("=== Base64 解密 ===");
//            String base64Decoded = new String(Base64Utils.decode(rsaDecoded));
//            System.out.println(base64Decoded);

            return rsaEncoded;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("网络请求参数加密出错");
        }
    }
}
