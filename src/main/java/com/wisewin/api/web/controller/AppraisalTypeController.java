package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.AppraisalTypeBo;
import com.wisewin.api.entity.bo.AppraisalTypeInfoBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.AppraisalTypeService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/appraisalType")
public class AppraisalTypeController extends BaseCotroller {
    @Resource
    private AppraisalTypeService appraisalTypeService ;

    @RequestMapping("/getAppraisalType")
    public void getAppraisalType(HttpServletRequest request, HttpServletResponse response){
        List<AppraisalTypeBo> banner=appraisalTypeService.getAppraisalType();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(banner)) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("/updAppraisalType")
    public void updAppraisalType(HttpServletRequest request, HttpServletResponse response,AppraisalTypeBo appraisalTypeBo){
        //验证参数
        if(appraisalTypeBo==null||appraisalTypeBo.getId()==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        appraisalTypeService.updAppraisalType(appraisalTypeBo);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("/addAppraisalType")
    public void addAppraisalType(HttpServletRequest request, HttpServletResponse response,AppraisalTypeBo appraisalTypeBo){
        //验证参数
        if(appraisalTypeBo==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        appraisalTypeService.addAppraisalType(appraisalTypeBo);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }


    /**
     * 鉴定分类详情
     * @param request
     * @param response
     */

    @RequestMapping("/queryTypeInfo")
    public void queryTypeInfo(HttpServletRequest request, HttpServletResponse response,Integer typeId){
        List<AppraisalTypeInfoBO> appraisalTypeInfoBOS = appraisalTypeService.queryTypeInfo(typeId);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(appraisalTypeInfoBOS)) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("/updTypeInfo")
    public void updTypeInfo(HttpServletRequest request, HttpServletResponse response,AppraisalTypeInfoBO appraisalTypeBo){
        //验证参数
        if(appraisalTypeBo==null||appraisalTypeBo.getId()==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        appraisalTypeService.updTypeInfo(appraisalTypeBo);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }


    @RequestMapping("/addTypeInfo")
    public void addTypeInfo(HttpServletRequest request, HttpServletResponse response,AppraisalTypeInfoBO appraisalTypeBo){
        //验证参数
        if(appraisalTypeBo==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        appraisalTypeService.addTypeInfo(appraisalTypeBo);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }



}