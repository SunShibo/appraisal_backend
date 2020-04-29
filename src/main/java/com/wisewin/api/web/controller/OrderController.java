package com.wisewin.api.web.controller;

import com.wisewin.api.common.base.BaseModel;
import com.wisewin.api.entity.bo.OrderItemBO;
import com.wisewin.api.entity.dto.ResultDTOBuilder;
import com.wisewin.api.query.QueryInfo;
import com.wisewin.api.service.OrderService;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.web.controller.base.BaseCotroller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseCotroller {

    @Resource
    OrderService orderService;

    /**
     * @param  orderNumber 订单号
     * @param  mobile 打赏人手机号
     * @param  afterTime 在这个时间之后（最小时间）
     * @param  beforeTime 在这个时间之前（最大时间）
     * */
    @RequestMapping("/getOrderList")
    public void getManagementReport(Integer pageSize,Integer pageNo,String orderNumber,String mobile,Date afterTime,Date beforeTime,HttpServletResponse response, HttpServletRequest request ){
        QueryInfo queryInfo = getQueryInfo(pageNo, pageSize);
        List<OrderItemBO> orderItemBOList =orderService.getOrderList(queryInfo.getPageOffset(),queryInfo.getPageSize(),orderNumber,mobile,afterTime,beforeTime);
        Integer count=orderService.getOrderCount(orderNumber,mobile,afterTime,beforeTime);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",orderItemBOList);
        map.put("count",count);
        String json = JsonUtils.getJsonString4JavaPOJO(ResultDTOBuilder.success(map));
        super.safeJsonPrint(response, json);
        return;
    }
}
