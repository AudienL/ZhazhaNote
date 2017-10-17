package com.diaozhatian.zhazhanote;

import com.diaozhatian.zhazhanote.http.ApiEncryptUtils;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        HashMap<String, Object> map = RSAUtil.getKeys();
//        //生成公钥和私钥
//        RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
//        RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
//
//        //模
//        String modulus = publicKey.getModulus().toString();
//        //公钥指数
//        String public_exponent = publicKey.getPublicExponent().toString();
//        //私钥指数
//        String private_exponent = privateKey.getPrivateExponent().toString();
//
//        System.out.println(modulus);
//        System.out.println(public_exponent);
//        System.out.println(private_exponent);

//        String ming = "123456";
//
//        String base64 = Base64Utils.encode(ming.getBytes());
//        System.out.println("=== Base64 ===");
//        System.out.println(base64);
//        System.out.println();
//
//        RSAPublicKey pubKey = RSAUtil.getPublicKey(Constants.API_RSA_MODULUS, Constants.API_RSA_PUBLIC_EXPONENT);
//        String mi = RSAUtil.encryptByPublicKey(base64, pubKey);
//        System.out.println("=== RSA ===");
//        System.out.println(mi);

//        //解密后的明文
//        ming = RSAUtil.decryptByPrivateKey(mi, priKey);
//        System.out.println(ming);
//        System.out.println("公钥指数: " + publicKey.getPublicExponent().toString());
//        System.out.println("私钥指数: " + privateKey.getPrivateExponent().toString());
//        System.out.println("模: " + privateKey.getModulus().toString());
//
//        //十六进制
//        System.out.println("公钥指数: " + publicKey.getPublicExponent().toString(16));
//        System.out.println("私钥指数: " + privateKey.getPrivateExponent().toString(16));
//        System.out.println("模: " + privateKey.getModulus().toString(16));


//        String modulus = "107920443544455768016677584168539218636510423190166749858207053111985549971685450681854223707183979763669480082097213075685642473312627579352159815373238385671496948488830337447224954167005534404920894925171926220403339853330972872444943790512686025398908275326951007383446583337775901191019706911982355423473";
//        String publicExponent = "65537";
//        String privateExponent = "63281424614643494653598774463475496787806262916748066440347267101644760972609368513251689318146591365763682348519367705044838100720676339272840525885807667731265402232660069105336272750663420074619814393519147990378183663405760952781287728971804665596000936871061241979644464544636949410411177702722256575153";
//
//        String password = "pppppp";
//
//        System.out.println("=== Base64 加密 ===");
//        String base64Encoded = Base64Utils.encode(password.getBytes());
//        System.out.println(base64Encoded);
//        System.out.println("=== RSA 加密 ===");
//        RSAPublicKey publicKey = RSAUtil.getPublicKey(modulus, publicExponent);
//        String rsaEncoded = RSAUtil.encryptByPublicKey(base64Encoded, publicKey);
//        System.out.println(rsaEncoded);
//
//        System.out.println("=== RSA 解密 ===");
//        RSAPrivateKey privateKey = RSAUtil.getPrivateKey(modulus, privateExponent);
//        String rsaDecoded = RSAUtil.decryptByPrivateKey(rsaEncoded, privateKey);
//        System.out.println(rsaDecoded);
//        System.out.println("=== Base64 解密 ===");
//        String base64Decoded = new String(Base64Utils.decode(rsaDecoded));
//        System.out.println(base64Decoded);

        String rsa = ApiEncryptUtils.encrypt("pppppp");
        System.out.println(rsa);
    }
}