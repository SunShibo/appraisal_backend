package com.wisewin.api.util.wxUtil.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


//发送微信请求配置类
public class WXRequestConfig {

    /**
     * 获取 App ID
     *
     * @return App ID
     */
    public String getAppID(){
        return WXConfig.APPID_JSAPI;
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
     public String getMchID(){
         return WXConfig.MCHID_JSAPI;
     }


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    public String getKey(){
        return WXConfig.KEY;
    }


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    public InputStream getCertStream() throws FileNotFoundException{
        //获取文件路径
        String fileName = this.getClass().getClassLoader().getResource("hehe.p12").getPath();
        InputStream  inputStream=new FileInputStream(fileName);
        return inputStream;
    }

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpConnectTimeoutMs() {
        return 6*1000;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpReadTimeoutMs() {
        return 8*1000;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     * @return
     */
    public IWXPayDomain getWXPayDomain(){
        return new IWXPayDomainImpl();
    }

    /**
     * 获取domain, 域名
     * @return
     */
    public String getDomain(){
        return "api.mch.weixin.qq.com";
    }

     /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    public boolean shouldAutoReport() {
        return true;
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    public int getReportWorkerNum() {
        return 6;
    }


    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    public int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    public int getReportBatchSize() {
        return 10;
    }

}
