package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.GradeBO;
import com.wisewin.api.entity.bo.KeyValueBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.GradeService;
import com.wisewin.api.service.KeyValueService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/keyValue")
public class KeyValueController extends BaseCotroller {
    @Resource
    private KeyValueService keyValueService ;

    @RequestMapping("getKeyValueList")
    public void getKeyValueList(HttpServletRequest request, HttpServletResponse response){
        List<KeyValueBO> gradeBOS=keyValueService.getKeyValueList();
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(gradeBOS)) ;
        super.safeJsonPrint(response, result);
    }
    @RequestMapping("updKeyValue")
    public void updKeyValue(HttpServletRequest request, HttpServletResponse response,KeyValueBO gradeBO){
        //验证参数
        if(gradeBO==null||gradeBO.getId()==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        keyValueService.updKeyValue(gradeBO);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }


}
