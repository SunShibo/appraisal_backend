package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.ResponseBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.CommentService;
import com.wisewin.api.util.DateUtils;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseCotroller {
    @Resource
    private CommentService commentService;

    @RequestMapping("/getCommentSum")
    public void getManagementReport(Date time, HttpServletResponse response, HttpServletRequest request ){
        try {

            AdminBO loginAdmin = super.getLoginUser(request);
            //验证用户
            if(loginAdmin==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
                super.safeJsonPrint(response, result);
                return ;
            }
            if (time==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
                super.safeJsonPrint(response, result);
                return;
            }

            String today = DateUtils.getStringData(time,"yyyy-MM-dd");
            String startTime = today+" 00:00:00";
            String endTime = today+" 23:59:59";
            Date startDate = DateUtils.parseDate(startTime,"yyyy-MM-dd HH:mm:ss");
            Date endDate =  DateUtils.parseDate(endTime,"yyyy-MM-dd HH:mm:ss");
            ResponseBO responseBO = commentService.selectSum(startDate,endDate);

            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(responseBO));
            super.safeJsonPrint(response, result);

        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.error("getManagementReportException",e);
        }
    }


    @RequestMapping("/addComment")
    public void addComment(HttpServletResponse response, HttpServletRequest request,Integer appraisalId,String cnComment,Integer userId,String judge){
        try {
            AdminBO loginAdmin = super.getLoginUser(request);
            //验证用户
            if(loginAdmin==null){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
                super.safeJsonPrint(response, result);
                return ;
            }
            if(appraisalId==null || userId==null || StringUtils.isEmpty(cnComment) || StringUtils.isEmpty(judge)){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000006"));
                super.safeJsonPrint(response, result);
                return;
            }
            commentService.addComment(appraisalId,userId,cnComment,judge);
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
            super.safeJsonPrint(response, result);
        }catch (Exception e){
            e.getStackTrace();
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
            super.safeJsonPrint(response, result);
            log.error("getManagementReportException",e);
        }
    }

    /**
     * 删除评论
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping("/deleteCommentById")
    public void deleteCommentById(HttpServletRequest request, HttpServletResponse response, Integer id){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        if(id == null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        int i = commentService.deleteCommentById(loginAdmin.getId(),id);
        if(i > 0){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
            super.safeJsonPrint(response, result);
            return;
        }
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000002" )) ;
        super.safeJsonPrint(response , result);
        return ;
    }


    /**
     * 采纳、取消采纳
     * @param request
     * @param response
     * @param id
     */
    @RequestMapping("/commentGoods")
    public void commentGoods(HttpServletRequest request, HttpServletResponse response, Integer id, String state){
        AdminBO loginUser = super.getLoginUser(request);
        if(loginUser==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000003"));
            super.safeJsonPrint(response, result);
            return;
        }
        //采纳评论  判断改鉴定下是否有其他已被采纳的评论，有返回提示
        if("yes".equals(state)){
            int i = commentService.selectCommentGoods(id);
            if(i > 0){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000100"));
                super.safeJsonPrint(response, result);
                return;
            }
            int yes = commentService.updateCommentGoods(loginUser.getId(),"yes", id);
            if(yes > 0){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
                super.safeJsonPrint(response, result);
                return;
            }
        }
        //取消采纳
        if("no".equals(state)){
            int yes = commentService.updateCommentGoods(loginUser.getId(),"no", id);
            if(yes > 0){
                String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
                super.safeJsonPrint(response, result);
                return;
            }
        }
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002"));
        super.safeJsonPrint(response, result);
        return;
    }


    @RequestMapping("/queryAcceptLog")
    public void queryAcceptLog(HttpServletRequest request, HttpServletResponse response,Date startTime,Date endTime,String userName, Integer pageNo,Integer pageSize){
        AdminBO loginUser = super.getLoginUser(request);
        if(loginUser==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000003"));
            super.safeJsonPrint(response, result);
            return;
        }
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        Map<String, Object> resultMap = commentService.queryLog(startTime, endTime, userName, queryInfo.getPageOffset(), queryInfo.getPageSize());
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(resultMap));
        super.safeJsonPrint(response, result);
        return;
    }
}
