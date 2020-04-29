package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.CommentBO;
import com.wisewin.api.entity.bo.ComtBO;
import com.wisewin.api.entity.bo.GradeBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommentDAO {

    int getCommentSum(Map<String,Object> map);//总评论数

    int getAppraisalSum(Map<String,Object> map);//商品发布数

    /**
     * 获取评论
     * @param map
     * @return
     */
    List<ComtBO> queryComment(@Param("map") Map<String, Object> map);

    /**
     * 获取评论条数
     * @param map
     * @return
     */
    int queryCommentCount(@Param("map")Map<String, Object> map);

    /**
     * 获取等级列表
     * @return
     */
    List<GradeBO> queryGradeList();

    /**
     * 获取被采纳数
     * @param integral
     * @return
     */
    Integer queryIntegral(@Param("integral")Integer integral);

    int insertComment(CommentBO commentBO);


    /**
     * 删除评论
     * @param id
     * @return
     */
    int deleteComment(@Param("id") Integer id);


    /**
     * 修改采纳状态
     * @param goodsState
     * @param id
     * @return
     */
    int updateCommentGoods(@Param("goodsState")String goodsState, @Param("id")Integer id);


    /**
     * 判断鉴定下是否有被采纳德评论
     * @param commentId
     * @return
     */
    int selectCommentGoods(@Param("commentId")Integer commentId);


    CommentBO  selectCommentStatusById(@Param("id")Integer id);




}
