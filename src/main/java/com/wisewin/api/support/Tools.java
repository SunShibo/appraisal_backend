package com.wisewin.api.support;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * UserBO: junyi.glj
 * Date: 13-12-1
 * Time: 下午4:46
 * 手机助手VM tools
 */
public class Tools {
    public static String substring(String src, int begin, int end) {
        if (src != null && src.length() > end) {
            return src.substring(begin, end)+"...";
        } else {
            return src;
        }
    }

    public static String substring2(String src, int begin, int end) {
        if (src != null && src.length() > end) {
            return src.substring(begin, end)+"***";
        } else {
            return src;
        }
    }

    /**
     * 安装量：从应用中心安装该应用的用户量，显示“***人安装”。
     * 0<安装量<=9999，显示“****人安装”；
     * 10000=<安装量<=49999,显示“*.*万人安装”，保留小数点后一位；
     * 50000=<安装量<=99994999,显示“****万人安装”，万位以前四舍五入；
     * 99995000=<安装量<10亿,显示“*.*亿人安装”，保留小数点后一位；
     * 10亿=<安装量<1亿亿，显示“****亿人安装”，亿位以前四舍五入。
     * @param number
     * @return
     */
    public static String parseNumberToLevel3(String number) throws Exception {
        if(!StringUtils.isNumeric(number)){
            return String.valueOf(0);
        }

        Long num = Long.parseLong(number);

        boolean fp = false;
        String unit = "";
        Double level = Double.parseDouble(number);
        level = round(level, 0);

        if(num >= 10000 && num <= 49999){
            level = round(num / 10000.0, 1);
            fp = true;
            unit = "万";
        }else if(num >= 50000 && num <= 99994999){
            level = round(num / 10000.0, 0);
            unit= "万";
        }else if(num >= 99995000 && num < 1000000000){
            level = round(num / 100000000.0, 1);
            fp = true;
            unit = "亿";
        }else if(num >= 1000000000){
            unit = "亿";
            level = round(num / 100000000.0, 0);
        }
        DecimalFormat df;
        df = fp ? new DecimalFormat("#.#") : new DecimalFormat("#");
        return df.format(level) + unit;
    }
    /**
     * 小数四舍五入
     * @param num 浮点数
     * @param precise  精确到的小数位数
     * @return
     */
    private static double round(double num, int precise) throws Exception {
        if(precise < 0){
            throw new Exception("精确小数位数不能为负值");
        }
        double pow = Math.pow(10.0, precise);
        double result = Math.round(num * pow) / pow;
        return  result;
    }

    public static String starGradeCal2(Integer number) {
        return starGradeNum(number).replace(".", "-");
    }
    public static String starGradeCal2(String number) {
        return starGradeNum(number).replace(".", "-");
    }
    /**
     * 返回评分数字
     * @author starty
     * @datetime 2012-12-17 下午2:37:37
     * @param number
     * @return
     */
    public static String starGradeNum(Object number) {
        long num;
        if(number != null)
            num = Long.valueOf(number.toString());
        else
            return "0";
        int grade = (int) (num / 2);
        String star = "";
        if (num%2 == 0) {
            star = grade + "";
        } else {
            star = grade + ".5";
        }
        return star;
    }
    public static String parseNumberToLevel2(String number){
        String level = parseNumberToLevel(number);
        if (level.equals("0"))
            return "0";
        else
            return level + "+";
    }
    /**
     * 将 某个数字转换为当前数量级
     *  0:0
     1-50:10+
     51-100:50+
     101-500:100+
     501-1000:500+
     1001-5000:1,000+
     5001-10000:5,000+
     10001-50000:10,000+
     50001-100000:50,000+
     100001-500000:100,000+
     500001-1000000:500,000+
     1000001-5000000:10,000,000+
     5000001-10000000:50,000,000+
     10000001-无限大:100,000,000+
     */
    public static String parseNumberToLevel(String number){
        Long level = 0L;
        if(! StringUtils.isNumeric(number)){
            return String.valueOf(level);
        }
        Long numberTemp = Long.parseLong(number);
        if(numberTemp > 0){
            if(numberTemp < 51){
                level = 10L;
            }else if(numberTemp > 10000000){
                level = 10000000L;
            }else{
                int digit = String.valueOf(numberTemp).length();
                long temp = (long) Math.pow(10, digit-1);
                if(numberTemp / temp < 5){
                    level =  temp;
                }else{
                    level = 5 * temp;
                    if(numberTemp.equals(level)){
                        level =  temp;
                    }
                }
            }
        }
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(level);
    }

    /**
     *  将apk包单位统一转换为兆 （M）
     */
    public static String changeSizeUnitToM(String appResourceSize){
        if(StringUtils.isBlank(appResourceSize)||StringUtils.isAlpha(appResourceSize)){
            //空字符串和只包含字母的均视为非法输入，初始化返回数据为0M
            return "0M";
        }
        if(StringUtils.contains(appResourceSize,"M") || StringUtils.contains(appResourceSize,"m")){
            return  appResourceSize;
        }else{
            if(StringUtils.contains(appResourceSize, "K") || StringUtils.contains(appResourceSize,"k")){
                appResourceSize = appResourceSize.substring(0,appResourceSize.length() -1 );
            }
            if(!StringUtils.isNumeric(appResourceSize)){
                return appResourceSize;
            }
            return divisionAndFormat(Integer.parseInt(appResourceSize),1024)+"M";
        }
    }

    private static String divisionAndFormat(long divisor, long dividend) {
        float result = ((float) divisor) / ((float) dividend) ;
        DecimalFormat df = new DecimalFormat("0.##");
        return df.format(result);
    }

    public static String urlEncoder(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static String encode(String content , String enc) {
        try {
            return URLEncoder.encode(content , enc);
        } catch (Exception e) {
            return "";
        }
    }

    public static String resolveCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String getRequestURLWithParameters(HttpServletRequest request) {
        String loginReturnUrl = request.getRequestURL().toString();
        String query = request.getQueryString();
        if (query != null) {
            loginReturnUrl = loginReturnUrl + "?" + query;
        }
        return loginReturnUrl;
    }

}
