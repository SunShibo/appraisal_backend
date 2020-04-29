package com.wisewin.api.web.support.referer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Created by Shibo on 2018/6/14.
 */
public class RefererViewResolver implements ViewResolver,Ordered {

    protected Log logger = LogFactory.getLog(getClass());
    //以referer:起始的viewName，将被此ViewResolver处理
    public static final String REFERER_PROFIX = "referer:";

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.ViewResolver#resolveViewName(java.lang.String, java.util.Locale)
     */
    public View resolveViewName(String viewName, Locale locale)
            throws Exception {
        if (!viewName.startsWith(REFERER_PROFIX)) {
            return null;
        }
        logger.debug("this is referer view.");
        return new RefererRedirectView();
    }

    /* (non-Javadoc)
     * @see org.springframework.core.Ordered#getOrder()
     */
    public int getOrder() {
        return Integer.MAX_VALUE-1;//这个ViewResolver排在UrlBasedViewResolver前一个的位置
    }

}