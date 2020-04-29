package com.wisewin.api.system.propertyPlaceholderPackage;

import com.wisewin.api.util.env.Env;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * Created by Shibo on 16/6/30.
 */
public class CustomPropertyConfigurer extends PropertyPlaceholderConfigurer{

    protected void processProperties( ConfigurableListableBeanFactory beanFactoryToProcess,
                                      Properties props) throws BeansException {

        for (Object tmp :  props.keySet()) {
            // 判断如果是diamond值得话需要在重新加载一遍
            if (props.get(tmp.toString()).equals(Env.DIAMOND_DEFAULT_VALUE))
                props.put(tmp.toString() , new Env().getDiamondValue(tmp.toString())) ;
         }

        super.processProperties(beanFactoryToProcess, props);
    }
}