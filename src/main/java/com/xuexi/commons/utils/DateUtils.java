package com.xuexi.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对日期类型进行处理的工具类
 */
public class DateUtils {
    /**
     * 对指定的date对象进行格式化：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formateDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDate = simpleDateFormat.format(date);
        return  newDate;
    }

    /**
     * 对指定的date对象进行格式化：yyyy-MM-dd
     * @return
     */
    public static String formateDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = simpleDateFormat.format(date);
        return  newDate;
    }

    /**
     * 对指定的date对象进行格式化： HH:mm:ss
     * @param date
     * @return
     */
    public static String formateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String newDate = simpleDateFormat.format(date);
        return  newDate;
    }
}
