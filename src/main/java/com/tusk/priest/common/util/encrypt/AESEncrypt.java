package com.tusk.priest.common.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AESEncrypt {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @param key     加密密钥
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] byteContent = content.getBytes(DEFAULT_CHARSET);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
            byte[] result = cipher.doFinal(byteContent);
            return Base64Encrypt.encode(result);
        } catch (Exception ex) {

        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
            byte[] result = cipher.doFinal(Base64Encrypt.decodeByte(content));
            return new String(result, DEFAULT_CHARSET);
        } catch (Exception ex) {
            Logger.getLogger(AESEncrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String key) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator keygen = null;
        try {
            keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            keygen.init(128, secureRandom);

//            keygen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = keygen.generateKey();


            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AESEncrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

}
