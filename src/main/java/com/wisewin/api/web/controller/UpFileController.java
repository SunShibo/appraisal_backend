package com.wisewin.api.web.controller;

import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.OSSClientUtil;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传与删除
 */
@Controller
@RequestMapping("/upFile")
public class UpFileController extends BaseCotroller {

    /**
     * 上传文件
     * @param request
     * @param response
     * @param file
     * @param flag
     * @throws Exception
     */
    @RequestMapping(value = "/upFile" ,headers = "content-type=multipart/*" , method = RequestMethod.POST)
    public void upFile( MultipartFile file,HttpServletRequest request, HttpServletResponse response,Boolean flag)
            throws Exception {
        //图片非空判断
        if (file==null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
        }
        OSSClientUtil ossClientUtil=new OSSClientUtil();
        //上传
        String name="";
        if(flag==null){
            name=ossClientUtil.uploadImg2Oss(file);
        }else{
            name=ossClientUtil.uploadImg2Oss(file);
        }
        //name:图片路径+图片名(图片名为生成的随机数)
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(name));
        super.safeJsonPrint(response,json);
    }



    //删除图片
    @RequestMapping("/delFile")
    public void delFile(String name,HttpServletResponse response,HttpServletRequest request) {
        if (StringUtils.isEmpty(name)){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response,json);
        }
        OSSClientUtil ossClientUtil=new OSSClientUtil();
        ossClientUtil.deleteFileInfo(name);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(null));
        super.safeJsonPrint(response,json);
    }


    @RequestMapping("/test")
    public void test(MultipartFile file,HttpServletResponse response) throws Exception {
        OSSClientUtil ossClientUtil=new OSSClientUtil();
        String s = ossClientUtil.uploadImg2Oss(file);
        System.out.println(s);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success
                (null));
        super.safeJsonPrint(response,json);
    }

}
