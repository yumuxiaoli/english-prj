package com.enstudy.demo.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;

import java.util.UUID;

public class MD5Util {

    /**
     * 对密码加密 md5
     * @param source  要加密的明文
     * @param salt  盐
     * @param hashIterations  散列次数
     * @return
     */
    public static String md5(String source, Object salt, Integer hashIterations) {
        return new Md5Hash(source, salt, hashIterations).toString();
    }


    /**
     * 对密码加密sha1
     * @param source  要加密的明文
     * @param salt  盐
     * @param hashIterations  散列次数
     * @return
     */
    public static String sha1(String source, Object salt, Integer hashIterations) {
        return new Sha1Hash(source, salt, hashIterations).toString();
    }

    public static void main(String[] args) {
        String password = "123456";
        //手动封装uid----uuid---随机不重复的字符串32位--java代码生成后是36位
        String salt = UUID.randomUUID().toString().replace("-","");
        System.out.println("加密盐值："+salt);
        System.out.println("加密后的密码："+ MD5Util.md5(password,salt,5));

    }

}
