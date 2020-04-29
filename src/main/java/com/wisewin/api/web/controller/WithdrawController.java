package com.wisewin.api.web.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.bo.VersionsBO;
import com.wisewin.api.entity.bo.WithdrawBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.AliPayService;
import com.wisewin.api.service.UserService;
import com.wisewin.api.service.WithdrawService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/withdraw")
public class WithdrawController extends BaseCotroller {

    @Resource
    WithdrawService withdrawService;

    @Resource
    UserService userService;

    @Resource
    AliPayService aliPayService;
    /**
     * 获取列表
     * @param afterTime  这个时间之后
     * @param beforeTime 这个时间之前
     * @param type       类型
     */
    @RequestMapping("/getWithdrawBOList")
    public void getWithdrawBOList(HttpServletRequest request, HttpServletResponse response, Integer pageNo, Integer pageSize, Date afterTime, Date beforeTime, String type) {
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        if (queryInfo != null) {
            List<WithdrawBO> list = withdrawService.getWithdrawBOList(queryInfo.getPageOffset(), queryInfo.getPageSize(), afterTime, beforeTime, type);
            Integer count = withdrawService.getWithdrawCount(afterTime, beforeTime, type);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("list", list);
            map.put("count", count);
            String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
            super.safeJsonPrint(response, json);
            return;
        }
    }

    /**
     * 修改提现状态
     * @param id   要修改的id
     * @param type 类型
     * @param apiMsg 信息
     */
    @RequestMapping("/updWithdrawBO")
    public void updWithdrawBO(HttpServletRequest request, HttpServletResponse response, Integer id, String type,String apiMsg)  {
        if (id==null ||type==null){
            String result= JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000001"));
            super.safeHtmlPrint(response,result);
            return;
        }
        AdminBO adminBO=super.getLoginUser(request);
        WithdrawBO withdrawBO = withdrawService.getWithdrawById(id);


        //转账是否成功标识
        boolean bool = false;
        //审核通过  进行转账
        if(type.equals("yes")){
            if(withdrawBO.getAccountType().equals("alipay")){
                try {
                    bool= aliPayService.aliPay(withdrawBO);
                } catch (AlipayApiException e) {
                    e.printStackTrace();
                    String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000101"));
                    super.safeJsonPrint(response, json);
                    return;
                }
            }else{
                String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.failure("0000000"));
                super.safeJsonPrint(response, json);
                return;
            }
        }

        //如果转账失败或者不通过  把提现金额退回
       if(bool || type.equals("no")){
           UserBO userBO=new UserBO();
           userBO.setMoney(withdrawBO.getMoney());
           userBO.setId(withdrawBO.getUserId());
           userService.updUserMoney(userBO);
           //修改体校记录状态
           if(type.equals("no")) { //手动拒绝
               withdrawBO.setType(type);
               withdrawBO.setApiMsg(apiMsg);
           }
       }

        withdrawBO.setAdminId(adminBO.getId());
        withdrawService.updWithdrawBO(withdrawBO);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success("0000000"));
        super.safeJsonPrint(response, json);
        return;
    }

}
