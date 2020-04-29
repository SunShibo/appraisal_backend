package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.common.constants.Result;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.UserService;
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
@RequestMapping("/user")
public class UserController extends BaseCotroller {
    @Resource
    private UserService userService ;

    @RequestMapping("/getUserList")
        public void getUserList(HttpServletRequest request, HttpServletResponse response,String watermarkState,
                                String phone,String name,Integer pageNo,Integer pageSize){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        List<UserBO> list=userService.getUserList(watermarkState,phone,name,queryInfo.getPageOffset(),queryInfo.getPageSize());
        Integer count=userService.getUserListCount(watermarkState, phone,name);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        map.put("totalCount",count);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map)) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("/querySystemUser")
    public void querySystemUser(HttpServletRequest request, HttpServletResponse response){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        List<UserBO> list=userService.querySystemUser();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(list)) ;
        super.safeJsonPrint(response, result);

    }

    @RequestMapping("/getUserById")
    public void getUserById(HttpServletRequest request, HttpServletResponse response,Integer id){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        // 非空判断
        if(id==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        UserBO userBO=userService.getUserById(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(userBO)) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("/updUser")
    public void updUser(HttpServletRequest request, HttpServletResponse response,UserBO userBO){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        // 非空判断
        if(userBO==null||userBO.getId()==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
       userService.updUser(userBO);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }


    /**
     * 修改水印状态
     * @param request
     * @param response
     */
    @RequestMapping("/updatewatermarkState")
    public void updatewatermarkState(HttpServletRequest request, HttpServletResponse response, Integer id, String watermarkState){
        Result result = userService.updatewatermarkState(id, watermarkState);
        String jsonString4JavaPOJO = JsonUtils.getJsonString4JavaPOJO(result);
        super.safeJsonPrint(response, jsonString4JavaPOJO);
        return ;
    }


}
