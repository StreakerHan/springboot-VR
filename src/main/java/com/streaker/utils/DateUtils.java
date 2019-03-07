package com.streaker.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author StreakerHan
 * @date 2019/2/25 11:22:32
 **/
public class DateUtils {

    public static String dateTransToChina(Date date){
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        return df.format(date);
    }
}
