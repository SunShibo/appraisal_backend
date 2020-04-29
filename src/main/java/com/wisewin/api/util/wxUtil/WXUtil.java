package com.wisewin.api.util.wxUtil;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class WXUtil {

    /**
     * @param code
     * @return  openId
     *
     */
   /* public static String  getAccessToken(String code){
        if (code==null || code.trim().length()<1){
            return null;
        }
        String resultStr = HttpRequest.sendGet(WXMessage.ACCESS_TOKEN_URL, "appid=" + WXMessage.APPID + "&" + "secret=" +
                            WXMessage.SECRET + "&" + "code=" + code + "&" + "grant_type=" + WXMessage.GRANT_TYPE);
        *//**
         *   access_token	接口调用凭证
             expires_in	access_token接口调用凭证超时时间，单位（秒）
             refresh_token	用户刷新access_token
             openid	授权用户唯一标识
             scope	用户授权的作用域，使用逗号（,）分隔
         *//*
        if(resultStr!=null) {
            Map<String, String> mapType = JSON.parseObject(resultStr, Map.class);
            String openid = mapType.get("openid");
            return openid;
        }
        return null;
    }*/



}
