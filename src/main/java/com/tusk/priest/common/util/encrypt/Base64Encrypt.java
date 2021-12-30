package com.tusk.priest.common.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64工具
 * @author alvin
 * @date 2018/8/21 15:14
 */
public class Base64Encrypt {

    /**
     * 加密JDK1.8
     * @param str
     * @return java.lang.String
     * @author alvin
     * @date 2018/8/21 15:28
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    public static String encode(byte[] str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str);
        return new String(encodeBytes);
    }


    /**
     * 解密JDK1.8
     * @param str
     * @return java.lang.String
     * @author alvin
     * @date 2018/8/21 15:28
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
    }

    public static String decode(byte[] str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str);
        return new String(decodeBytes);
    }

    public static byte[] decodeByte(String str) throws UnsupportedEncodingException {
        return Base64.getDecoder().decode(str.getBytes("utf-8"));
    }
}
