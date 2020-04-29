package com.wisewin.api.service;

import com.wisewin.api.dao.OrderDAO;
import com.wisewin.api.entity.bo.OrderItemBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Resource
    OrderDAO orderDAO;

    //获取订单列表
    public List<OrderItemBO> getOrderList(Integer pageOffset, Integer pageSize, String orderNumber, String mobile, Date afterTime, Date beforeTime) {
        return orderDAO.getOrderList(pageOffset, pageSize, orderNumber, mobile, afterTime, beforeTime);
    }
    //获取订单列表总数量
    public Integer getOrderCount(String orderNumber, String mobile, Date afterTime, Date beforeTime) {
        return orderDAO.getOrderListCount(orderNumber, mobile, afterTime, beforeTime);
    }

}

