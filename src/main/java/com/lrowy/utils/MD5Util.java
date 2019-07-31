package com.lrowy.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String encrypt(String dataStr) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(dataStr.getBytes(StandardCharsets.UTF_8));
        byte[] s = m.digest();
        StringBuilder result = new StringBuilder();
        for (byte b : s) {
            result.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00)
                    .substring(6));
        }
        return result.toString();
    }
}
