package com.wisewin.api.web.controller;


import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.AppraisalBO;
import com.wisewin.api.entity.bo.ReisedUserBO;
import com.wisewin.api.entity.dto.AppraisalDTO;
import com.wisewin.api.entity.dto.CommentDTO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.AppraisalService;
import com.wisewin.api.service.CommentService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wang bin
 * @date: Created in 13:32 2019/9/5
 */
@Controller
@RequestMapping("/appraisal")
public class AppraisalController extends BaseCotroller {

    @Autowired
    private AppraisalService appraisalService;

    @Autowired
    private CommentService commentService;

    /**
     * 获取鉴定列表
     * @param request
     * @param response
     */
    @RequestMapping("/queryAppraisal")
    public void queryAppraisal(HttpServletRequest request, HttpServletResponse response, String title,
                               String appraisalState, Integer appraisalTypeId,String reisedState, String sta,
            String apCase, Integer pageNo, Integer pageSize){
        AdminBO loginUser = super.getLoginUser(request);
        if(loginUser == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000003"));
            super.safeJsonPrint(response, json);
            return;
        }

        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        //创建一个用于封装sql条件的map集合
        Map<String, Object> condition = new HashMap<String, Object>();
        if (queryInfo != null) {
            //把pageOffset 页数,pageSize每页的条数放入map集合中
            condition.put("pageOffset", queryInfo.getPageOffset());
            condition.put("pageSize", queryInfo.getPageSize());
        }
        condition.put("title", title);
        condition.put("appraisalState", appraisalState);
        condition.put("appraisalTypeId", appraisalTypeId);
        condition.put("reisedState", reisedState);
        condition.put("apCase", apCase);
        condition.put("status", sta);
        Map<String, Object> appraisalDTOS = appraisalService.listAppraisal(condition);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(appraisalDTOS));
        super.safeJsonPrint(response, json);
        return;
    }

    @RequestMapping("/saveAppraisalCase")
    public void saveAppraisalCase(HttpServletRequest request, HttpServletResponse response, Integer id){
        appraisalService.updateAppraisalCase(id);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
        super.safeJsonPrint(response, json);
        return;
    }


    /**
     * 获取鉴定详情
     * @param request
     * @param response
     * @param appraisalId 鉴定 id
     */
    @RequestMapping("/appraisalDetails")
    public void appraisalDetails(HttpServletRequest request, HttpServletResponse response, Integer appraisalId){
        if(appraisalId == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        Map<String, Object> appraisalBO = appraisalService.appraisalDetails(appraisalId);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(appraisalBO));
        super.safeJsonPrint(response, json);
        return;
    }


    @RequestMapping("/appraisalComment")
    public void appraisalComment(HttpServletRequest request, HttpServletResponse response, Integer appraisalId, Integer pageNo, Integer pageSize){
        if(appraisalId == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        QueryInfo queryInfo=getQueryInfo(pageNo,pageSize);
        Map<String, Object> commentBOS=commentService.getCommentByAppraisalId(appraisalId,queryInfo.getPageOffset(),queryInfo.getPageSize());
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(commentBOS));
        super.safeJsonPrint(response,json);
        return;
    }


    /**
     * 获取纠错列表
     * @param request
     * @param response
     */
    @RequestMapping("/apprasialReised")
    public void apprasialReised(HttpServletRequest request, HttpServletResponse response,
                                Integer commentId, Integer pageNo, Integer pageSize){
        QueryInfo queryInfo=getQueryInfo(pageNo,pageSize);
        if(commentId == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        List<ReisedUserBO> reisedUserBOS = appraisalService.queryReisedList(commentId, queryInfo.getPageOffset(), queryInfo.getPageSize());
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(reisedUserBOS));
        super.safeJsonPrint(response,json);
        return;
    }


    @RequestMapping("/updateAppraisal")
    public void updateApprasial(HttpServletRequest request, HttpServletResponse response, AppraisalBO appraisal1){
        if(appraisal1.getId() == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        int i = appraisalService.updateAppraisal(appraisal1);
        if(i > 0){
            String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
            super.safeJsonPrint(response,json);
            return;
        }
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
        super.safeJsonPrint(response,json);
        return;
    }


    /**
     * 鉴定上下架
     * @param request
     * @param response
     * @param state
     * @param id
     */
    @RequestMapping("/saveAppState")
    public void saveAppState(HttpServletRequest request, HttpServletResponse response, String state, Integer id){
        int i = appraisalService.updateAppraisalStateById(state, id);
        if(i > 0){
            String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
            super.safeJsonPrint(response,json);
            return;
        }
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
        super.safeJsonPrint(response,json);
        return;
    }

    /**
     * 修改纠错状态
     * @param request
     * @param response
     * @param reisedState
     * @param id
     */
    @RequestMapping("/saveAppReisedState")
    public void saveAppReisedState(HttpServletRequest request, HttpServletResponse response,
                                   String reisedState, Integer id){
        if(reisedState == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        if(id == null){
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeJsonPrint(response, json);
            return;
        }
        int i = appraisalService.updateAppraisalreisedState(reisedState, id);
        if(i > 0){
            String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
            super.safeJsonPrint(response,json);
            return;
        }
        String json= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
        super.safeJsonPrint(response,json);
        return;
    }
}
