package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.GradeBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.GradeService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/grade")
public class GradeController extends BaseCotroller {
    @Resource
    private GradeService gradeService ;

    @RequestMapping("getGradeBOList")
    public void getBannerList(HttpServletRequest request, HttpServletResponse response){
        List<GradeBO> gradeBOS=gradeService.getGradeBOList();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(gradeBOS)) ;
        super.safeJsonPrint(response, result);
    }
    @RequestMapping("delGrade")
    public void delBanner(HttpServletRequest request, HttpServletResponse response,Integer id){
        //验证参数
        if(id==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        gradeService.delGrade(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("updGrade")
    public void updBanner(HttpServletRequest request, HttpServletResponse response,GradeBO gradeBO){
        //验证参数
        if(gradeBO==null||gradeBO.getId()==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        gradeService.updGrade(gradeBO);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("addGrade")
    public void addGrade(HttpServletRequest request, HttpServletResponse response,GradeBO gradeBO){
        //验证参数
        if(gradeBO==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        gradeService.addGrade(gradeBO);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }
}
