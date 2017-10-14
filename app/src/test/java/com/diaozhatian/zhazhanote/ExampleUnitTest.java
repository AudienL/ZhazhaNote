package com.diaozhatian.zhazhanote;

import com.audienl.superlibrary.utils.encrypt.RSAUtils;

import org.junit.Test;

import java.security.KeyPair;

/**
 * Example local unit test, which will execute on the development machine (host).
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        for (int i = 0; i < 3; i++) {
            KeyPair keyPair = RSAUtils.generateRSAKeyPair();
            RSAUtils.printPublicKeyInfo(keyPair.getPublic());
            RSAUtils.printPrivateKeyInfo(keyPair.getPrivate());
            System.out.println();
            System.out.println("==================================================");
            System.out.println();

            System.out.println(new String(RSAUtils.encryptData("123456".getBytes(), keyPair.getPublic())));
        }
    }
}