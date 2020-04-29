package com.wisewin.api.web.controller;

import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.BannerBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.service.BannerService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.StringUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/banner")
public class BannerController extends BaseCotroller {
    @Resource
    private BannerService bannerService ;

    @RequestMapping("getBannerList")
    public void getBannerList(String state,HttpServletRequest request, HttpServletResponse response){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        List<BannerBO> banner=bannerService.getBanner(state);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(banner)) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("getBannerById")
    public void getBannerById(HttpServletRequest request, HttpServletResponse response,Integer id){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(id==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        BannerBO banner=bannerService.getBannerById(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(banner)) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("delBanner")
    public void delBanner(HttpServletRequest request, HttpServletResponse response,Integer id){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(id==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        bannerService.delBanner(id);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("updBanner")
    public void updBanner(HttpServletRequest request, HttpServletResponse response,BannerBO banner){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" , "用户未登录")) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(banner==null||banner.getId()==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" , "参数异常")) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        banner.setUpdateUserId(loginAdmin.getId());
        bannerService.updBannerBO(banner);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }

    @RequestMapping("addBanner")
    public void addBanner(HttpServletRequest request, HttpServletResponse response,BannerBO banner){
        AdminBO loginAdmin = super.getLoginUser(request);
        //验证用户
        if(loginAdmin==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000002" )) ;
            super.safeJsonPrint(response, result);
            return ;
        }
        //验证参数
        if(banner==null){
            String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001" )) ;
            super.safeJsonPrint(response , result);
            return ;
        }
        banner.setCreateUserId(loginAdmin.getId());
        bannerService.addBannerBO(banner);
        String result = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000")) ;
        super.safeJsonPrint(response, result);
    }
}
