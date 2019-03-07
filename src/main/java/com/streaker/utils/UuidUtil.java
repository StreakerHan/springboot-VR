package com.streaker.utils;

/**
 * id生成策略工具类
 *
 * @author StreakerHan
 * @date 2019/2/26 14:13:47
 **/
public class UuidUtil {

    /**
     * 20位末尾的数字id
     */
    public static int Guid=100;

    public static String getGuid() {

        UuidUtil.Guid+=1;

        long now = System.currentTimeMillis();
        //获取4位年份数字
        //SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");
        //获取时间戳
        //String time=dateFormat.format(now);
        String info=now+"";
        //获取三位随机数
        //int ran=(int) ((Math.random()*9+1)*100);
        //要是一段时间内的数据连过大会有重复的情况，所以做以下修改
        int ran=0;
        if(UuidUtil.Guid>999){
            UuidUtil.Guid=100;
        }
        ran=UuidUtil.Guid;

        return info.substring(2, info.length())+ran;
    }
}
