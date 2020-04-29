package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.ReisedBO;
import com.wisewin.api.entity.bo.ReisedUserBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Wang bin
 * @date: Created in 10:57 2019/9/9
 */
public interface ReisedDAO {

    /**
     * 获取纠错列表
     * @param map
     * @return
     */
    List<ReisedBO> queryReisedList(Map<String, Object> map);

    /**
     * 获取采纳评论下的纠错评论
     * @param map
     * @return
     */
    List<ReisedUserBO> queryReised(@Param("map") Map<String,Object> map);

    ReisedBO  selectReisedById(Integer reisedId);

    int updateCommentByAppraisalId(Integer appraisalId);


    int updateReisedById(ReisedBO reised);


    /**
     * 将被采纳的纠错列为采纳状态
     * @param id
     * @return
     */
    int updateReisedStatus(@Param("id")Integer id);
}
