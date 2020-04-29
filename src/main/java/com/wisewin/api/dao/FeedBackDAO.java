package com.wisewin.api.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Wang bin
 * @date: Created in 13:35 2019/10/10
 */
public interface FeedBackDAO {
    List<FeedBackDAO> feedBackList(@Param("describc")String describc,
                                   @Param("apStatus")String apStatus,
                                   @Param("pageNo")Integer pageNo,
                                   @Param("pageSize")Integer pageSize);

    int feedBackCount(@Param("describc")String describc,
                                   @Param("apStatus")String apStatus);

    int updateFeedBack(@Param("apStatus")String apStatus,@Param("id")Integer id);


}
