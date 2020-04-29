package com.wisewin.api.web.support.referer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Shibo on 2018/6/14.
 */
public class RefererRedirectView implements View {

    protected static final Log logger = LogFactory.getLog(RefererRedirectView.class);

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.View#getContentType()
     */
    public String getContentType() {
        return null;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.View#render(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void render(Map model, HttpServletRequest request,
                       HttpServletResponse response) throws Exception {

        String returnUrl = request.getHeader("referer");
        if(returnUrl==null || returnUrl.trim().equals("")){
            returnUrl = "/";
        }
        logger.debug("referer url["+returnUrl+"]");
        response.sendRedirect(returnUrl);
    }

}