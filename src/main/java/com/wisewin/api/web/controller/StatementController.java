package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.StatisticalBO;
import com.wisewin.api.entity.bo.VersionsBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.StatementService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/statementController")
public class StatementController extends BaseCotroller {

    @Resource
    StatementService statementService;

    @RequestMapping("/getStatement")
    public void getStatement(HttpServletRequest request, HttpServletResponse response,  Date date) {
        if (date == null) {
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response, json);
            return;
        }
        List<StatisticalBO> statisticalBOS =statementService.getStatement(date);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(statisticalBOS));
        super.safeJsonPrint(response, result);
    }


}
