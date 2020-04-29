package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.AcceptLogBO;
import com.wisewin.api.entity.dto.AcceptLogDTO;
import com.wisewin.api.entity.bo.AppraisalBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Wang bin
 * @date: Created in 17:50 2019/9/4
 */
public interface AppraisalDAO {

    /**
     * 获取鉴定列表
     * @param map
     * @return
     */
    List<AppraisalBO> queryAppraisal(@Param("map")Map<String, Object> map);

    /**
     * 获取鉴定条数
     * @param map
     * @return
     */
    Integer queryAppraisalConunt(@Param("map")Map<String, Object> map);

    /**
     * 设置鉴定案例
     * @param map
     * @return
     */
    int updateAppraisalCase(Map<String, Object> map);

    /**
     * 根据id查询鉴定
     * @param appraisalId
     * @return
     */
    AppraisalBO queryappraisalById(@Param("appraisalId")Integer appraisalId);


    int updateAppraisal(AppraisalBO appraisalBO);

    /**
     * 上下架
     * @param state
     * @param id
     * @return
     */
    int updateAppraisalStateById(@Param("state")String state,@Param("id")Integer id);

    void addComment(@Param("appraisalId") Integer appraisalId, @Param("userId") Integer userId, @Param("cnComment")
            String cnComment,@Param("judge") String judge);

    void addLog(AcceptLogDTO logBO);

    List<AcceptLogBO> queryLog(Map<String, Object> paramMap);

    int queryLogCount(Map<String, Object> paramMap);

    int updateAppraisalreisedState(@Param("reisedState")String reisedState, @Param("id")Integer id);
}
