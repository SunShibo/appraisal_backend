package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.StatementBO;
import org.apache.ibatis.annotations.Param;

public interface StatementDAO {
    //获取评论数
    Integer getCommentCount(@Param("year")Integer year, @Param("month")Integer month, @Param("day")Integer day);
    //获取商品发布数
    Integer getAppraisalCount(@Param("year")Integer year, @Param("month")Integer month, @Param("day")Integer day);
    //获取注册数 and 活跃数
    StatementBO getRegisteredCountAndActiveCount(@Param("year")Integer year, @Param("month")Integer month, @Param("day")Integer day);

}
