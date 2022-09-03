package com.yyok.share.framework.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;

/**
 * @fun 国密
 * @author root
 */
public class SMHutoolUtil {

    public static void main(String[] args) {
        System.out.println("======== 国密:SM2-4使用密钥对加密或解密，开始 ========");
        String pwd ="123456";
        test1("123456");
        test2();
        test3();
        test4();
        test5();
        test6();
        sm2(pwd);
        sm3(pwd);
        sm4(pwd);
    }

    public static void test1(String pwd) {

       // System.out.println("======== SM2使用随机生成的密钥对加密或解密，开始 ========");

        String text = "SM2使用随机生成的密钥对加密或解密";
        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(pwd, KeyType.PublicKey);
        System.out.println("密文：" + encryptStr);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println("明文：" + decryptStr);

        System.out.println("======== SM2使用随机生成的密钥对加密或解密，结束 ========");
    }

    public static void test2() {
        System.out.println("======== SM2使用自定义密钥对加密或解密，开始 ========");

        String text = "SM2使用自定义密钥对加密或解密";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        System.out.println("私钥："+ Base64.encodeBase64String(privateKey));
        byte[] publicKey = pair.getPublic().getEncoded();
        System.out.println("公钥："+ Base64.encodeBase64String(publicKey));

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        System.out.println("密文：" + encryptStr);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println("明文：" + decryptStr);

        System.out.println("======== SM2使用随机生成的密钥对加密或解密，结束 ========");
    }

    public static void test3() {
        System.out.println("======== SM2签名和验签，开始 ========");

        String content = "SM2签名和验签";
        final SM2 sm2 = SmUtil.sm2();
        String sign = sm2.signHex(HexUtil.encodeHexStr(content));
        System.out.println("sign:" + sign);

        boolean verify = sm2.verifyHex(HexUtil.encodeHexStr(content), sign);
        System.out.println("校验结果为：" + verify);

        System.out.println("======== SM2签名和验签，结束 ========");
    }

    public static void test4() {
        System.out.println("======== 摘要加密算法SM3，开始 ========");

        String text = "摘要加密算法SM3";
        System.out.println("text:" + text);
        String digestHex = SmUtil.sm3(text);
        System.out.println("digestHex：" + digestHex);

        System.out.println("======== 摘要加密算法SM3，结束 ========");
    }

    public static void test5() {
        System.out.println("======== 对称加密SM4，开始 ========");

        String content = "对称加密SM4";
        SymmetricCrypto sm4 = SmUtil.sm4();

        String encryptHex = sm4.encryptHex(content);
        System.out.println("密文：" + encryptHex);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("明文：" + decryptStr);

        System.out.println("======== 对称加密SM4，结束 ========");
    }

    /***
     * 分开的：私钥签名，公钥验签
     */
    public static void test6(){
        System.out.println("======== 私钥签名公钥验签，开始 ========");
        String txt = "私钥签名公钥验签";

        String privateKey = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgfVNnk3eKogEiEAvYinFGZev31dT4gQqcEAAidzIKhC2gCgYIKoEcz1UBgi2hRANCAATolVEVuON8S9aOpnogLTzXzo0Dx58LMFjex7XPPcPtSmuq1Rh/ZM2qbYdZyzdnca8eCeR+cg+44rb/TyPRlH23";
        String publicKey = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE6JVRFbjjfEvWjqZ6IC08186NA8efCzBY3se1zz3D7UprqtUYf2TNqm2HWcs3Z3GvHgnkfnIPuOK2/08j0ZR9tw==";

        SM2 sm2Sign = SmUtil.sm2(privateKey, null);
        byte[] sign = sm2Sign.sign(txt.getBytes(), null);
        System.out.println("sign:" + Base64.encodeBase64String(sign));

        SM2 sm2 = SmUtil.sm2(null, publicKey);
        boolean verify = sm2.verify(txt.getBytes(), sign);
        System.out.print("verify:" + verify);
        System.out.println("======== 私钥签名公钥验签，结束 ========");
    }

    public static void sm2(String pwd) {

        //使用随机生成的密钥对加密或解密
        System.out.println("使用随机生成的密钥对加密或解密====开始");
        SM2 sm2 = SmUtil.sm2();
        // 公钥加密
        String encryptStr = sm2.encryptBcd(pwd, KeyType.PublicKey);
        System.out.println("公钥加密：" + encryptStr);
        //私钥解密
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        System.out.println("私钥解密：" + decryptStr);
        System.out.println("使用随机生成的密钥对加密或解密====结束");


        //使用自定义密钥对加密或解密
        System.out.println("使用自定义密钥对加密或解密====开始");

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        SM2 sm22 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密
        String encryptStr2 = sm22.encryptBcd(pwd, KeyType.PublicKey);
        System.out.println("公钥加密：" + encryptStr2);
        //私钥解密
        String decryptStr2 = StrUtil.utf8Str(sm22.decryptFromBcd(encryptStr2, KeyType.PrivateKey));
        System.out.println("私钥解密：" + decryptStr2);
        System.out.println("使用自定义密钥对加密或解密====结束");

    }

    public static void sm3(String pwd) {
        String digestHex = SmUtil.sm3(pwd);
        System.out.println("加密后：" + digestHex);
    }

    public static void sm4(String pwd) {
        SymmetricCrypto sm4 = SmUtil.sm4();
        String encryptHex = sm4.encryptHex(pwd);
        System.out.println("加密后：" + encryptHex);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("解密后：" + decryptStr);
    }

}
