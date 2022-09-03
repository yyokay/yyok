package com.yyok.share.framework.utils;

/**
 * 引进的包都是Java自带的jar包
 * 秘钥相关包
 * base64 编解码
 * 这里只用到了编码
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;

public class SecrteKeyUtil {


    public class Keys {

    }

    public static final String RSA_KEY = "RSA";
    //国密
    public static final String ALGORITHM_SM4="SM4";

    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    public static String RSA_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    private static KeyFactory keyFactory = null;

    public static String PrivateEx="70395";//""4abbae70395...";
    public static String PrivateMo="806";//"c8c156b0806..."; // modules
    public static String PublicEx="10001";
    public static String PublicMo="806";//"c8c156b0806..."; // modules


    public static String UTF8 = "UTF-8";

    static {
        try {
            keyFactory = KeyFactory.getInstance(RSA_KEY, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException ex) {
        }
    }
    
    /**
     * 通过私钥byte[]将公钥还原，适用于RSA算法
     *
     * @param modulus
     * @param exponent
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) throws Exception {
        RSAPrivateKeySpec privateKey = new RSAPrivateKeySpec(new BigInteger(modulus,16), new BigInteger(exponent,16));
        return (RSAPrivateKey) keyFactory.generatePrivate(privateKey);
    }

    /**
     *
     * @param privateKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        RSAPrivateKey rsaKey = (RSAPrivateKey) privateKey;
        Cipher ci = Cipher.getInstance(RSA_ALGORITHM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, rsaKey);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Integer index = 0;
        Integer blockSize = rsaKey.getModulus().bitLength() / 8;
        byte[] buffer = new byte[blockSize];
        while ((index = inputStream.read(buffer, 0, blockSize)) > 0) {
            byte[] clpherBlock = ci.doFinal(buffer, 0, index);
            outputStream.write(clpherBlock);
        }

        byte[] result= ((ByteArrayOutputStream) outputStream).toByteArray();
        outputStream.flush();
        return result;
    }

    /**
     * @param privateKey
     * @param encrypttext
     * @return
     */
    public static String decryptString(PrivateKey privateKey, String encrypttext) {
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            return new String(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param encryption 需要解密的串 "3532a86906a..."
     * @Rigorous Test :-)
     */
    public static void shouldAnswerWithTrue(String encryption) {
        String exponent = PrivateEx;
        String modulus =PrivateMo;
        try {
            String result = decryptString(getPrivateKey(modulus, exponent),encryption);
            System.out.println("解密结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @ fun 获得公钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * @fun 获得私钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //byte[] privateKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    /**
     * @fun 解码返回byte
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * @fun 编码返回字符串
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * @fun map对象中存放公私钥
     * @param len
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey(int len, String algorithm,String pwd) throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
        SecureRandom sr =  new SecureRandom();
        byte[] bt = pwd.getBytes("UTF8");
        //System.out.println(Arrays.toString(bt));
        sr.nextBytes(bt);
        //System.out.println(Arrays.toString(bt));
        keyPairGen.initialize(len,sr);
        // 密
        //Cipher cipher = Cipher.getInstance(algorithm);
        //cipher.init(Cipher.ENCRYPT_MODE, keyPairGen);
        //byte[] bytes = cipher.doFinal(pwd.getBytes());
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 实例化公钥
     *
     * @return
     */
    private static PublicKey getPubKey(String pubKey) {
        PublicKey publicKey = null;
        try {
            java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(pubKey));
            // RSA对称加密算法
            java.security.KeyFactory keyFactory;
            keyFactory = java.security.KeyFactory.getInstance(RSA_KEY);
            // 取公钥匙对象
            publicKey = keyFactory.generatePublic(bobPubKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 实例化私钥
     *
     * @return
     */
    private static PrivateKey getPrivateKey(String priKey) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec priPKCS8;
        try {
            priPKCS8 = new PKCS8EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(priKey));
            KeyFactory keyf = KeyFactory.getInstance(RSA_KEY);
            privateKey = keyf.generatePrivate(priPKCS8);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static void main(String[] args) {
        Map<String, Object> keyMap;
        try {
            int len = 1024;
            String pwd = "0987";
            String filePath = "/";
            //SM1-4 ;  RSA_KEY
            keyMap = initKey(len,RSA_KEY,pwd);
            String publicKey = getPublicKey(keyMap);
            System.out.println("publicKey = "+publicKey);
            String privateKey = getPrivateKey(keyMap);
            PublicKey pbk = getPubKey(publicKey);
            PrivateKey prik = getPrivateKey(privateKey);
            System.out.println("privateKey = "+privateKey);
            // 保存公私钥到文件
            FileUtils.writeStringToFile(new File(filePath+"/publicKey"), publicKey, Charset.forName("UTF-8"));
            FileUtils.writeStringToFile(new File(filePath+"/privateKey"), privateKey, Charset.forName("UTF-8"));
            //shouldAnswerWithTrue("3532a86906a");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}