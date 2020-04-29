package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.SensitivityBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.SensitivityService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
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
@RequestMapping("/sensitivity")
public class SensitivityController extends BaseCotroller {


    @Resource
    private SensitivityService sensitivityService;

    /**
     * 添加
     */
    @RequestMapping("/add")
    public void add(HttpServletRequest request, HttpServletResponse response,String str){
        if (StringUtils.isEmpty(str)){
            String languagejson= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        sensitivityService.add(str);

        String languagejson= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
        super.safeHtmlPrint(response,languagejson);
        return;
    }


    /**
     * 查询
     */
    @RequestMapping("/query")
    public void query(HttpServletRequest request,HttpServletResponse response,Integer pageNo,Integer pageSize){
        QueryInfo queryInfo = getQueryInfo(pageNo,pageSize);
        if(queryInfo != null){
            List<SensitivityBO> query = sensitivityService.query(queryInfo.getPageOffset(), queryInfo.getPageSize());
            Integer count=sensitivityService.selectCount();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("list",query);
            map.put("count",count);
            String json=JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response,json);
            return;
        }
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    public void deleteVersions(HttpServletRequest request,HttpServletResponse response,Integer id){
        if (id==null){
            String languagejson= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,languagejson);
            return;
        }
        sensitivityService.delete(id);
        String languagejson= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
        super.safeHtmlPrint(response,languagejson);
        return;
    }


}
