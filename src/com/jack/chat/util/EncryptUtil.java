package com.jack.chat.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 加密工具
 * @author Jinkang He
 * @version 1.0
 * @date 2020/4/11 14:08
 */

public class EncryptUtil {
    public static String  encrypt(String str) {
        BigInteger bigInteger = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = str.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bigInteger.toString();
    }
}
