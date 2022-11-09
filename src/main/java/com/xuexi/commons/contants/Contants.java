package com.xuexi.commons.contants;

public class Contants  {
    //保存ReturnObject类中code的值
    public static final Integer RETURN_OBJECT_CODE_SUCCESS= 1;  // 成功

        public static final Integer RETURN_OBJECT_CODE_FAIL = 0;      //失败

    //写几个枚举
    public  enum code{
        SUCCESS,FAIL,
    }

    //保存当前用户的key
    public static final String SESSION_USER_KEY="SessionUser";

    public static final String EDIT_FLAG_YES="1";

    public static final String EDIT_FLAG_NO="0";
}
