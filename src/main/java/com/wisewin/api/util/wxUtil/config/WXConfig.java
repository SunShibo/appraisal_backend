package com.wisewin.api.util.wxUtil.config;

import com.wisewin.api.util.wxUtil.WXPayUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//微信配置信息类
public class WXConfig {
    //统一下单 接口
    public static  String  PLACE_AN_ORDERAPI;

    //查询订单 接口
    public static  String  PLACE_AN_QURERYAPI;

    //密钥 MD5生成的（多语咖）
    public static  String  KEY;

    //appid
    public static String  APPID_JSAPI;

    //所支付的名称
    private static String  BODY;

    //商户号
    public  static String  MCHID_JSAPI;

    //咖豆通知地址
    public static String  NOTIFY_URL_CURRENCY;

    //语言通知地址
    public static String  NOTIFY_URL_LANGUAGE;

    //课程通知地址
    public static String  NOTIFY_URL_COURSE;

    //服务器地址
    private static String  SPBILL_CREATE_IP;

    //证书名字
    public static String  THE_CERTIFICATE_OF_JSAPI;

    //初始化需要的配置信息
    static {
        InputStream is = WXConfig.class.getResourceAsStream("/wx_pay_config.properties");
        Properties prop = new Properties();
        try {
            prop.load(is);
            PLACE_AN_ORDERAPI=prop.getProperty("PLACE_AN_ORDERAPI");
            PLACE_AN_QURERYAPI=prop.getProperty("PLACE_AN_QURERYAPI");
            KEY=prop.getProperty("KEY");
            APPID_JSAPI=prop.getProperty("APPID_JSAPI");
            BODY=prop.getProperty("BODY");
            MCHID_JSAPI=prop.getProperty("MCHID_JSAPI");
            NOTIFY_URL_CURRENCY=prop.getProperty("NOTIFY_URL_CURRENCY");
            NOTIFY_URL_LANGUAGE=prop.getProperty("NOTIFY_URL_LANGUAGE");
            NOTIFY_URL_COURSE=prop.getProperty("NOTIFY_URL_COURSE");
            SPBILL_CREATE_IP=prop.getProperty("SPBILL_CREATE_IP");
            THE_CERTIFICATE_OF_JSAPI=prop.getProperty("THE_CERTIFICATE_OF_JSAPI");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //初始化请求Map
    public static Map<String,String> toMapJSAPI(){
        Map<String,String> map=new HashMap<String, String>();
        map.put("appid",APPID_JSAPI);//公众号id
        map.put("mch_id",MCHID_JSAPI);//商户号
        map.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        map.put("body",BODY);//商品描述
        map.put("spbill_create_ip",SPBILL_CREATE_IP);//终端ip地址
        map.put("trade_type","APP");//交易类型
        return  map;
    }

}