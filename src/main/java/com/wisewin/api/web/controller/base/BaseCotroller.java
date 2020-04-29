package com.wisewin.api.web.controller.base;

import com.google.common.collect.Lists;
import com.wisewin.api.common.constants.SysConstants;
import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.query.PageObject;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.util.redisUtils.RedissonHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Iterator;
import java.util.List;


/**
 * @author Shibo Sun
 */
public class BaseCotroller {
    protected transient final Logger log = Logger.getLogger(getClass());

//    @Resource( name = "loginService" )
//    LoginService loginService;

    protected PageObject pager = null;
    private int default_page_size = 10;
    private int default_page_no = 1;

    /**
     * 异步返回结果
     *
     * @param response
     * @param str
     */
    public void safeJsonPrint(HttpServletResponse response, String str) {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.write(str);
        } finally {
            if (out != null)
                out.close();
        }
    }

    /**
     * 异步返回结果
     *
     * @param response
     * @param str
     */
    public void safeTextPrint(HttpServletResponse response, String str) {
        response.setContentType("text/plain;charset=UTF-8");
        System.out.println("返回值信息：" + str);
        PrintWriter out = null;
        try {
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.write(str);
        } finally {
            if (out != null)
                out.close();
        }
    }

    public void safeHtmlPrint(HttpServletResponse response, String str) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.write(str);
        } finally {
            if (out != null)
                out.close();
        }
    }


    /**
     * 此方法防止当缓冲区满之后将无法继续写入数据，最终造成阻塞在waitFor()这里
     * @param process
     * @return
     */
    public int safeRun(Process process){
        try {
            // 保存进程的输入流信息
            List<String> stdoutList = Lists.newArrayList();
            // 保存进程的错误流信息
            List<String> erroroutList = Lists.newArrayList();

            // 先清空
            stdoutList.clear();
            erroroutList.clear();

            // 创建2个线程，分别读取输入流缓冲区和错误流缓冲区
            ThreadUtil stdoutUtil = new ThreadUtil(process.getInputStream(), stdoutList);
            ThreadUtil erroroutUtil = new ThreadUtil(process.getErrorStream(), erroroutList);
            //启动线程读取缓冲区数据
            stdoutUtil.start();
            erroroutUtil.start();

            return process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    class ThreadUtil implements Runnable {
        // 设置读取的字符编码
        private String character = "UTF-8";
        private List<String> list;
        private InputStream inputStream;

        public ThreadUtil(InputStream inputStream, List<String> list) {
            this.inputStream = inputStream;
            this.list = list;
        }

        public void start() {
            Thread thread = new Thread(this);
            thread.setDaemon(true);//将其设置为守护线程
            thread.start();
        }

        public void run() {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(inputStream, character));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line != null) {
                        if(!br.ready()){
                            break;
                        }
                        list.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    //释放资源
                    inputStream.close();
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *获取各种类型表单的表单参数
     *@paramrequest HttpServletRequest请求对像
     * @paramparamName 参数名
     *@return
     *@throwsFileUploadException
     */
    public static String getParameterValue(HttpServletRequest request, String paramName) {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(isMultipart==true){
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List fileItemList = null;
            try {
                fileItemList = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            if(fileItemList!=null){
                for(Iterator itr = fileItemList.iterator(); itr.hasNext();){
                    FileItem fileItem = (FileItem)itr.next();
                    if(fileItem.getFieldName().equalsIgnoreCase(paramName)){
                        try {
                            return new String(fileItem.getString().getBytes("ISO8859-1"));//中文转码
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else{
            try {
                return new String(request.getParameter(paramName).getBytes("ISO8859-1"));//中文转码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return"";
    }

    /** 移除session*/
    public void removeSession (HttpServletRequest request , String key) {
        String loginID = this.getLoginID(request);

        RedissonHandler.getInstance().delete(createKey(this.getLoginID(request), key));
//        RedisUtil.del(createKey(this.getLoginID(request), key)) ;
    }

    /** 获取登录用户*/
    public AdminBO getLoginUser (HttpServletRequest request ) {
        Object obj=this.getSession(request, SysConstants.CURRENT_LOGIN_USER);
        if(obj==null){
            return null;
        }
        return (AdminBO) obj ;
    }

    /** putLoginUser*/
    public void putLoginAdmin (String loginId , AdminBO loginUser) {
        this.putSession(createKey(loginId, SysConstants.CURRENT_LOGIN_USER), loginUser) ;
    }


//    /** 获取登录用户*/
//    public ClientInfo getLoginClientInfo (HttpServletRequest request ) {
//        return (ClientInfo)this.getClientSession(request, SysConstants.CURRENT_LOGIN_CLIENT) ;
//    }
//
//    /** putLoginUser*/
//    public void putLoginClientInfo (String loginId , ClientInfo clientInfo) {
//        this.putSession(createKey(loginId, SysConstants.CURRENT_LOGIN_CLIENT), clientInfo) ;
//    }

    /** putSession */
    public void putSession (HttpServletRequest request, String key , String value ) {
        this.putSession(createKey(this.getLoginID(request), key), value) ;
    }
    /**
     * 获取登录ID (从cookie中获取)
     * @param request
     * @return
     */
    public String getLoginID(HttpServletRequest request) {

        return getCookie(request , SysConstants.CURRENT_LOGIN_ID) ;
    }

    public String getClientLoginID(HttpServletRequest request) {
        return getCookie(request , SysConstants.CURRENT_LOGIN_CLIENT_ID) ;
    }

    public void putPublicSession (String key , Object value) {
        RedissonHandler.getInstance().set(key , value , null);
//        RedisUtil.set(value , key) ;
    }

    public Object getPublicSession(String key) {
        return RedissonHandler.getInstance().get(key);
//        return RedisUtil.get(key) ;
    }

    public void removePublicSession(String key) {
        RedissonHandler.getInstance().delete(key);
//        RedisUtil.del(key) ;
    }
    /**
     * session赋值
     */
    private void putSession (String key , Object value) {
        RedissonHandler.getInstance().set(key , value , null);
//        RedisUtil.set(value , key) ;
    }

    /**
     * 生成在redis中存储的key
     * @param loginId 登录用户ID
     * @param key key
     * @return
     */
    public String createKey (String loginId, String key) {
        return loginId + "@" + key ;
    }

    /**
     * 获取session
     * session存储格式为
     * loginUuid + @ + key
     * */
    public Object getSession (HttpServletRequest request , String key) {
        return RedissonHandler.getInstance().get(createKey(this.getClientLoginID(request), key));
//        return RedisUtil.get(createKey(this.getLoginID(request), key)) ;
    }

    public Object getClientSession (HttpServletRequest request , String key) {
        return RedissonHandler.getInstance().get(createKey(this.getClientLoginID(request), key));
//        return RedisUtil.get(createKey(this.getLoginID(request), key)) ;
    }

    public String getCookie(HttpServletRequest request , String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null ;
        }
        for(Cookie c :cookies ){
            if (c.getName().equals(key)) {
                return c.getValue() ;
            }
        }
        return null ;
    }

    public void removeCookie (HttpServletRequest request , HttpServletResponse response , String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return  ;
        }
        for(Cookie c :cookies ){
            if (c.getName().equals(key)) {
                c.setMaxAge(0);
                c.setValue(null);
                c.setPath("/");
                response.addCookie(c);
            }
        }


    }

    public void setCookie(HttpServletResponse response, String key , String value , int expiry) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry); //365 * 24 * 60 * 60
        cookie.setPath("/");
        response.addCookie(cookie);
    }
//
//    /**
//     * 更新登录用户在缓存中的信息
//     * @param request
//     */
//    public void updateLoginUserProfile(HttpServletRequest request ) {
//        UserBO loginUser = this.getLoginUser(request);
//        UserBO newLoginUser = loginService.loginByIdNoPwd(loginUser.getId());
//        newLoginUser.setUuid(loginUser.getUuid());
//        this.putLoginUser(loginUser.getUuid() , newLoginUser);
//    }

    public QueryInfo getQueryInfo(Integer pageNo, Integer pageSize) {
        if (null == pageNo) {
            pageNo = default_page_no;
        }
        if (null == pageSize) {
            pageSize = default_page_size;
        }
        if (null != pageNo) {
            QueryInfo queryInfo = new QueryInfo(pageSize, null, null,
                    (pageNo - 1) * pageSize);
            return queryInfo;
        }
        return null;
    }

    public PageObject getPager() {
        return pager;
    }

    public void setPager(PageObject pager) {
        this.pager = pager;
    }

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

//    public void setLoginClientInfo(ClientInfo clientInfo) {
//        sput(SysConstants.CURRENT_CLIENT_INFO, clientInfo);
//    }

    public void removeLoginClientInfo() {
        sremove(SysConstants.CURRENT_CLIENT_INFO);
    }

    protected void sremove(String key) {
        getSession().removeAttribute(key);
    }

    protected void sput(String key, Object value) {
        getSession().setAttribute(key, value);
    }

//    protected ClientInfo sgetLoginUser() {
//        Object obj = getSession().getAttribute(SysConstants.CURRENT_CLIENT_INFO);
//        if (obj == null ){
//            return null;
//        }
//        return (ClientInfo) obj;
//    }

    protected HttpSession getSession() {
        return getRequest().getSession();
    }
}
