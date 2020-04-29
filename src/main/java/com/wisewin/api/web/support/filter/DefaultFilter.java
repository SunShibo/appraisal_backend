package com.wisewin.api.web.support.filter;

import com.wisewin.api.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sunshibo on 2016/4/11.
 */
public class DefaultFilter extends OncePerRequestFilter {

    static final Logger log = LoggerFactory.getLogger(DefaultFilter.class);

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.info("URl:{} \t content type:{}  ", request.getRequestURI(), request.getContentType());
        log.info("param:{}  ", JsonUtils.getJsonString4JavaPOJO(request.getParameterMap()));
        //到过滤器链的下一个过滤器
        filterChain.doFilter(request, response);
    }
}