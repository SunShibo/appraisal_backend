package com.wisewin.api.web.controller;

import com.wisewin.api.dao.FeedBackDAO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.FeedBackService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wang bin
 * @date: Created in 14:09 2019/10/10
 */
@Controller
@RequestMapping("/feedBack")
public class FeedBackController extends BaseCotroller {

    @Autowired
    private FeedBackService feedBackService;

    @RequestMapping("/feedBackList")
    public void feedBackList(HttpServletRequest request, HttpServletResponse response,
                             String describc,String apStatus,
                             Integer pageNo, Integer pageSize){
        QueryInfo queryInfo=getQueryInfo(pageNo,pageSize);
        Map<String, Object> feedBackDAOS = feedBackService.queryFeedBack(describc, apStatus, queryInfo.getPageOffset(), queryInfo.getPageSize());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(feedBackDAOS));
        super.safeJsonPrint(response, result);
        return;
    }

    @RequestMapping("/saveFeedBack")
    public void saveFeedBack(HttpServletRequest request, HttpServletResponse response,String status, Integer id){
        int i = feedBackService.updateFeed(status, id);
        if(i > 0){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
            super.safeJsonPrint(response, result);
            return;
        }
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000002"));
        super.safeJsonPrint(response, result);
        return;
    }
}
