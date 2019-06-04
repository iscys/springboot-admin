package com.project.model;

import java.math.BigInteger;
import java.security.MessageDigest;

public  class  MessageModel {


    public static class  ErrorMessage {
        public static final String PHONENULL = "1001";
        public static final String PHONENULLMESSAGE = "手机号不能为空";
        public static final String PASSNULL = "1002";
        public static final String PASSNULLMESSAGE = "密码不能为空";
        public static final String USERNAMENULL = "1003";
        public static final String USERNAMENULLMESSAGE = "账号不能为空";
        public static final String USERNULL = "1004";
        public static final String USERNULLMESSAGE = "账号或者密码错误";
    }


    public static String getMD5String(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            //一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方）
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        System.out.println(getMD5String("123456"));
    }



}
