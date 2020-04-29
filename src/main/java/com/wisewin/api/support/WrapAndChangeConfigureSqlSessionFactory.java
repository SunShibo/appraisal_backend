package com.wisewin.api.support;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

/**
 * Created with IntelliJ IDEA.
 * UserBO: Shibo Sun
 * Date: 4/14/13
 * Time: 1:32 PM
 *
 * 修改请参考 org.apache.ibatis.builder.xml.XMLConfigBuilder#settingsElement(org.apache.ibatis.parsing.XNode)
 * 这里的目的是 原生的 SqlSessionFactoryBean 在非xml 模式下 不能配置 Configuration
 * 由于用到了 setMapUnderscoreToCamelCase 所以这样添加
 * 直到 Mybatis 官方提供解决方案
 */
public class WrapAndChangeConfigureSqlSessionFactory extends SqlSessionFactoryBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        SqlSessionFactory sqlSessionFactory = super.getObject();

        Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
    }
}
