package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.OrderItemBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderDAO {
    /*
     * 获取订单列表
     * */
    List<OrderItemBO> getOrderList(@Param("pageOffset")Integer pageOffset, @Param("pageSize")Integer pageSize,@Param("orderNumber")String orderNumber, @Param("mobile")String mobile, @Param("afterTime")Date afterTime, @Param("beforeTime")Date beforeTime);

    /*
     * 获取订单数量
     * */
    Integer getOrderListCount(@Param("orderNumber")String orderNumber, @Param("mobile")String mobile, @Param("afterTime")Date afterTime, @Param("beforeTime")Date beforeTime);

}
