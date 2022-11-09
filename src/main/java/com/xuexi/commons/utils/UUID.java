package com.xuexi.commons.utils;


/**
 * 获取UUID的静态公共类的方法
 */
public class UUID {
    public static String getuuid(){
        String s = java.util.UUID.randomUUID().toString().replaceAll("-","");
        return s;
    }
}
