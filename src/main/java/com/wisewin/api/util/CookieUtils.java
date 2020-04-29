package com.wisewin.api.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author Administrator
 *
 */
public class CookieUtils {
	
	public void saveCookie(String key, String value, HttpServletResponse response) {
		saveCookie(key, value, -1, null,response);
	}

	public void saveCookie(String key, String value, int maxAge, String path
			, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		if (maxAge != -1) {
			cookie.setMaxAge(maxAge);
		}
		if (null != path && !"".equals(path)) {
			cookie.setPath(path);
		} else {
			cookie.setPath("/");
		}
		response.addCookie(cookie);
	}
	
	/**
	 * 根据名字获取cookie
	 * @param request
	 * @return
	 */
	public static Cookie getCookieByName(String key, HttpServletRequest request){
	    Map<String,Cookie> cookieMap = readCookieMap(request);
	    if(cookieMap.containsKey(key)){
	        Cookie cookie = (Cookie)cookieMap.get(key);
	        return cookie;
	    }else{
	        return null;
	    }  
	}
	
	/**
	 * 将cookie封装到Map里面
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}

}
