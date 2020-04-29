package com.wisewin.api.util.message;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.log4j.Logger;


/**
 * Created by Administrator on 2018/3/27.
 */
public class SendMessageUtil {
    private final static Logger log = Logger.getLogger(SendMessageUtil.class);

    static String accessKeyId="LTAIrYEzjhGJigUM";
    static String accessSecret="KG9W4CwP3kAjnNEx9eiU40XtboouNZ";

 /*   private static final int APPID = 1400077931;
    private static final String APPKEY = "f0f83079a2c7fd091a039a04bc260741";*/

    //腾讯短信通道
   /* public static void sendSignInCodeMessage(String mobile, String content){
        try {
            SmsSingleSender sender = new SmsSingleSender(APPID, APPKEY);
            ArrayList<String> params = new ArrayList<String>();
            params.add(content);
            SmsSingleSenderResult result = sender.sendWithParam("86", mobile, 98018, params, "", "", "");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public static void sendSignInCodeMessage(String mobile, String content){
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "多语咖");
        request.putQueryParameter("TemplateCode", "SMS_165412502");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+content+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }



}
