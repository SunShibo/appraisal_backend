package com.wisewin.api.util.env;

import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by sunshibo on 2016/9/30.
 */

@Service("env")
public class Env {

    static final Logger log = LoggerFactory.getLogger(Env.class);
    /**
     * 自动扫描路径
     */
    public static final Resource[] DEFAULT_ENV_RESOURCES = ResourceUtil.getResources(new String[]
            {"classpath:conf/locale/*.properties"}, true);

    public static final String DEFAULT_ENV_SOURCE_NAME = "env";

    private String envSourceName = DEFAULT_ENV_SOURCE_NAME;

    private String diamondDefaultGroupId = "diamond.groupId" ;

    public static final String DIAMOND_DEFAULT_VALUE = "${diamond}" ;

    public static volatile boolean diamondSwitch = true ;

    /**
     * 获取value = 1.localProperties + 2.diamond
     * @param key
     * @return
     */
    public String getProperty(String key){
        String value = this.getLocalProperty(key) ;
        if (value != null && !value.equals(DIAMOND_DEFAULT_VALUE))
            return value ;
        if (diamondSwitch) {
            value = this.getDiamondValue(key) ;
        }
        return value ;
    }

    /**
     * 带默认值获取value
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProperty(String key , String defaultValue) {
        String value = this.getProperty(key) ;
        return  value == null ? defaultValue : value ;
    }

    /**
     * 从diamond中获取value ;
     * @param key
     * @return
     */
    public String getDiamondValue (String key) {
        String groupId = this.getLocalProperty(diamondDefaultGroupId) ;
        return this.getDiamondValue(groupId , key) ;
    }

    /**
     * groupId + dataId 的形式获取diamond中的值
     * @param groupId
     * @param key
     * @return
     */
    public String getDiamondValue(String groupId , String key ) {
        DiamondManager manager = new DefaultDiamondManager(groupId, key,
                new ManagerListener() {

                    public Executor getExecutor() {
                        return null;
                    }
                    public void receiveConfigInfo(String configInfo) {
                        System.out.println("获取配置信息:"+ configInfo);
                    }
                });
        // 一般的获取信息方法
        String configInfo = manager.getAvailableConfigureInfomation(1000);
        return configInfo ;
    }

    /**
     * 获取本地properties文件中的值
     * @param key
     * @return
     */
    public String getLocalProperty (String key) {
        for (Resource resource : DEFAULT_ENV_RESOURCES) {
            if (resource.exists()) {
                Properties p = new Properties() ;
                try {
                    p.load(resource.getInputStream());
                } catch (IOException e) {
                    log.error("[Env - getProperty]  error:" + e);
                    return null ;
                }
                if (p.getProperty(key) != null ) {
                    return p.getProperty(key) ;
                }
            }
        }
        return null ;
    }

    public static void main(String[] args) {
        System.out.println(new Env().getDiamondValue("testaaddsfd"));
    }

}
