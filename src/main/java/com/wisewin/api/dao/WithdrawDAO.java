package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.WithdrawBO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface WithdrawDAO {
    //获取提现列表
    List<WithdrawBO> getWithdrawBOList(@Param("pageOffset") Integer pageOffset, @Param("pageSize") Integer pageSize, @Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime, @Param("type") String type);
    //获取提现列表数量
    Integer getWithdrawCount(@Param("afterTime") Date afterTime, @Param("beforeTime") Date beforeTime, @Param("type") String type);
    //通过id获取提现详情
    WithdrawBO getWithdrawById(Integer id);
    //修改提现信息
    Integer updWithdrawBO(WithdrawBO withdrawBO);

}
