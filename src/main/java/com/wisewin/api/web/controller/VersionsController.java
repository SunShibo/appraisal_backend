package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.VersionsBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.VersionsService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Versions")
public class VersionsController extends BaseCotroller {


    @Resource
    private VersionsService versionsService;

    /**
     * 添加版本
     */
    @RequestMapping("/addVersions")
    public void addVersions(HttpServletRequest request, HttpServletResponse response,VersionsBO versionsBO){
        //创建获取管理员id
        AdminBO loginAdmin = super.getLoginUser(request);
        if(loginAdmin==null){
            String languagejson= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000003"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        Integer id = loginAdmin.getId();
        if (id==null ||versionsBO==null){
            String languagejson= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        versionsBO.setAdminId(id);
        if (versionsService.getaddVersions(versionsBO)){
            String result= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
            super.safeJsonPrint(response,result);
        }else{
            String result= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeHtmlPrint(response,result);
        }

    }


    /**
     * 通过版本查询
     */
    @RequestMapping("/queryVersions")
    public void queryVersions(HttpServletRequest request,HttpServletResponse response,Integer pageNo,Integer pageSize){

        AdminBO loginAdmin = super.getLoginUser(request);
        //根据版本号来查询
        QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
        if(queryInfo != null){
            List<VersionsBO> queryVersionsjson=versionsService.getqueryVersions(queryInfo.getPageOffset(),queryInfo.getPageSize());
            Integer count=versionsService.selectVersionBOCount();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("queryVersionsjson",queryVersionsjson);
            map.put("count",count);
            String json=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response,json);
            return;
        }
    }


    /**
     * 删除
     */
    @RequestMapping("/deleteVersions")
    public void deleteVersions(HttpServletRequest request,HttpServletResponse response,Integer vid){
        if (vid==null||vid.equals("")){
            String languagejson= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        boolean deleteVersionsjson=versionsService.getdeleteVersions(vid);
        if (deleteVersionsjson){
            String languagejson=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
            super.safeJsonPrint(response,languagejson);
            return;
        }
        String languagejson= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
        super.safeHtmlPrint(response,languagejson);
        return;
    }
}
