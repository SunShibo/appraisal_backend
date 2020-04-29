package com.wisewin.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Wang bin
 * @date: Created in 12:03 2019/9/5
 */
public class OrderUtil {

    /**
     * 生成订单号
     * @return
     */
    public static String getNumber(){
        StringBuffer stringBuffer = new StringBuffer();
        Date t = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        stringBuffer.append(df.format(t));
        int ran  = (int)((Math.random()*9+1)*100000);
        stringBuffer.append(String.valueOf(ran));
        return stringBuffer.toString();
    }
}
