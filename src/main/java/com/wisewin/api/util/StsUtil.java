package com.wisewin.api.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import java.util.HashMap;
import java.util.Map;


public class StsUtil {


    private static final String accessKeyId = "LTAI4Fh8fZ5NnybeVePiiHhT";
    private static final String accessKeySecret = "WyfGvNha7SIkz9T9SB9aMfmYX3UuKX";
    // 目前只有"cn-hangzhou"这个region可用，不要使用填写其他region的值
    public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
    // 当前 STS API 版本
    public static final String STS_API_VERSION = "2015-04-01";

    static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
                                         String roleArn, String roleSessionName, String policy,
                                         ProtocolType protocolType) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client，用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(STS_API_VERSION);
       //     request.setDurationSeconds(900L);
            request.setMethod(MethodType.POST);
            request.setProtocol(protocolType);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            // 发起请求，并得到response
            final AssumeRoleResponse response = client.getAcsResponse(request);
            return response;
        } catch (ClientException e) {
            throw e;
        }
    }

    /**
     * 获取临时STS
     * @param roleSessionName
     * @return
     */
    public static Map<String,String> getStsMessage(String roleSessionName)  {
        Map<String,String>  resultMap =  new HashMap<String, String>();
        // 定制你的policy
        String policy = "{\n" +
                "  \"Version\": \"1\",\n" +
                "  \"Statement\": [\n" +
                "    {\n" +
                "      \"Action\": \"vod:*\",\n" +
                "      \"Resource\": \"*\",\n" +
                "      \"Effect\": \"Allow\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;
        final AssumeRoleResponse response;
        try {
            response = assumeRole(accessKeyId, accessKeySecret,"acs:ram::1744068782260169:role/oss", roleSessionName, policy, protocolType);
            resultMap.put("akId",response.getCredentials().getAccessKeyId());
            resultMap.put("akScret",response.getCredentials().getAccessKeySecret());
            resultMap.put("stk", response.getCredentials().getSecurityToken());
            resultMap.put("reqId", response.getRequestId());
            resultMap.put("Expiration",response.getCredentials().getExpiration());
        } catch (ClientException e){
            resultMap.put("errorCode",e.getErrCode());
            resultMap.put("errorMessage", e.getErrMsg());
        }
        return  resultMap;
    }


    /**
     * 获取临时OSS
     * @param roleSessionName
     * @return
     */
    public static Map<String,String> getStsOss(String roleSessionName){
        Map<String,String>  resultMap =  new HashMap<String, String>();
        // 定制你的policy
        String policy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:*\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        // 此处必须为 HTTPS
        ProtocolType protocolType = ProtocolType.HTTPS;
        final AssumeRoleResponse response;
        try {
            response = assumeRole(accessKeyId, accessKeySecret,"acs:ram::1744068782260169:role/oss", roleSessionName, policy, protocolType);
            resultMap.put("expiration",response.getCredentials().getExpiration());
            resultMap.put("tempAk",response.getCredentials().getAccessKeyId());
            resultMap.put("tempSk",response.getCredentials().getAccessKeySecret());
            resultMap.put("token", response.getCredentials().getSecurityToken());
        } catch (ClientException e){
            resultMap.put("errorCode",e.getErrCode());
            resultMap.put("errorMessage", e.getErrMsg());
        }
        return  resultMap;
    }





    static void createUploadVideo(String accessKeyId, String accessKeySecret, String token) {
        String regionId = "cn-shanghai"; // 点播服务所在的Region，国内请填cn-shanghai，不要填写别的区域
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setSecurityToken(token);
        request.setTitle("t1");
        request.setFileName("file1.mp4");
        request.setFileSize(10240L);
        try {
            CreateUploadVideoResponse response = client.getAcsResponse(request);
            System.out.println("CreateUploadVideoRequest, " + request.getUrl());
            System.out.println("CreateUploadVideoRequest, requestId:" + response.getRequestId());
            System.out.println("UploadAddress, " + response.getUploadAddress());
            System.out.println("UploadAuth, " + response.getUploadAuth());
            System.out.println("VideoId, " + response.getVideoId());
        } catch (ClientException e) {
            System.out.println("action, error:" + e);
            e.printStackTrace();
        }
    }


}