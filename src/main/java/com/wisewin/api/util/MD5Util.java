package com.wisewin.api.util;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 */
public class MD5Util {

    static MessageDigest md;
    static {
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (Exception ex) {
        }
    }

    /**
     * 加密方法
     *
     * @param msg
     * @return
     */
    public static String digest(String msg) {
        byte[] rlt = md.digest(msg.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rlt.length; i++) {
            String d = "00" + Integer.toHexString(rlt[i]);
            sb.append(d.substring(d.length() - 2));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(digest("多语种联盟"));
   }
}
