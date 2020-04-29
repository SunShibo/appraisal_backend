package com.wisewin.api.util.wxUtil;

import com.wisewin.api.pop.SystemConfig;

public class WXMessage {
    /**
     *  通过code获取access_token的接口
     */
    public static String ACCESS_TOKEN_URL= SystemConfig.getString("access_token");
    /**
     * appid 应用唯一标识，在微信开放平台提交应用审核通过后获得
     */
    public static String APPID=SystemConfig.getString("appid");
    /**
     * 应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
     */
    public static String SECRET=SystemConfig.getString("secret");

    /**
     *   grant_type 填 authorization_code
     */
    public static String GRANT_TYPE=SystemConfig.getString("grant_type");



}
