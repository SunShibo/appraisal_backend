package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.dto.ReisedDTO;
import com.wisewin.api.entity.dto.ResultDTO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.ReisedService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wang bin
 * @date: Created in 12:11 2019/9/9
 */
@Controller
@RequestMapping("/reised")
public class ReisedController extends BaseCotroller {

    @Autowired
    private ReisedService reisedService;

    @RequestMapping("/queryReised")
    public void queryReised(HttpServletRequest request, HttpServletResponse response,
                            Integer pageOffset, Integer pageSize, Integer id,
                            String status, Integer apRead){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        QueryInfo queryInfo = getQueryInfo(pageOffset, pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if (queryInfo != null) {
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }
        condition.put("id", id);
        condition.put("status", status);
        condition.put("apRead", apRead);
        List<ReisedDTO> reisedDTOS = reisedService.listReised(condition);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(reisedDTOS));
        super.safeJsonPrint(response, json);
        return;
    }


    /**
     * 将纠错列为采纳
     * @param request
     * @param response
     * @param reisedId
     */
    @RequestMapping("/adoptReised")
    public void adoptReised(HttpServletRequest request,HttpServletResponse response,Integer reisedId){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        ResultDTO resultDTO = reisedService.adoptReised(loginAdmin.getId(),reisedId);
        super.safeJsonPrint(response, JsonUtils.getJsonString4JavaPOJO(resultDTO));
        return;
    }
}
