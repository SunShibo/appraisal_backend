package com.wisewin.api.common.constants;

/**
 * Created by sunshibo on 2016/1/4.
 */
public class SysConstants {
    /**所有权限*/
    public static final String ALL_PERMISSION = "all_permission";

    //静态资源文件夹
    public static final String OSS_FOLDER_STATIC = "static/";
    //JS文件夹
    public static final String OSS_FOLDER_STATIC_JS = "static/js/";
    //静态样式文件夹
    public static final String OSS_FOLDER_STATIC_CSS = "static/css/";
    //静态图片文件夹
    public static final String OSS_FOLDER_STATIC_IMG = "static/img/";
    //业务（屏保、壁纸）资源文件夹
    public static final String OSS_FOLDER_RES = "res/";
    //屏保资源文件夹
    public static final String OSS_FOLDER_RES_SAVER = "res/saver/";
    //壁纸资源文件夹
    public static final String OSS_FOLDER_RES_WALLPAPER = "res/wallpaper/";

    /**
     * 当前用户登录的uuid
     */
    public static final String CURRENT_LOGIN_ID = "userLoginID";

    public static final String CURRENT_LOGIN_CLIENT_ID = "clientLoginID";

    /**
     * 登录用户id
     */
    public static final String CURRENT_USER_ID = "userID";

    /**
     * 登录用户
     */
    public static final String CURRENT_LOGIN_USER = "currentLoginAdmin";

    public static final String CURRENT_LOGIN_CLIENT = "currentLoginClient";

    public static final String CURRENT_LOGIN_LAST_URL = "lastURL";

    public static final String CURRENT_CLIENT_INFO = "currentClient";

    /**
     * 一分钟的毫秒数
     */
    public static final int MINUTE_TIME = 60 * 1000 ;

    /**
     * 一天的毫秒数
     */
    public static final int OND_DAY_TIME = 24 * 60 * MINUTE_TIME ;

    /**
     * 7天的毫秒数
     */
    public static final int SEVEN_DAY_TIME = OND_DAY_TIME * 7 ;

    /**
     * 合法的IMEI号的长度
     */
    public static int VALID_IMEI_LENGTH = 15;
    /**
     * 合法的MEID号的长度
     */
    public static int VALID_MEID_LENGTH = 14;
    /**
     * 合法的UUID的长度
     */
    public static int VALID_UUID_LENGTH = 32;

    /**
     *  文件上传进度的前缀
     *  用于放在tair中做前缀名
     */
    public static final String FILE_UPLOAD_PROGRESSED_ = "SUP_" ;

    /**
     *  上传进度
     */
    public static final String UPLOAD_PROGRESSED = "upload_progressed" ;
}
