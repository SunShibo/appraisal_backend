package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.AppraisalBO;
import com.wisewin.api.entity.bo.AppraisalTypeBo;
import com.wisewin.api.entity.bo.AppraisalTypeInfoBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Wang bin
 * @date: Created in 15:48 2019/9/25
 */
public interface AppraisalTypeDAO {

    /**
     * 获取鉴定类型列表
     * @return
     */
    List<AppraisalTypeBo> getAppraisalType();

    //修改
    Integer updAppraisalType(AppraisalTypeBo appraisalTypeBo);
    //添加
    Integer addAppraisalType(AppraisalTypeBo appraisalTypeBo);

    /**
     * 鉴定分类详情列-表
     */
    List<AppraisalTypeInfoBO> queryTypeInfo(@Param("typeId")Integer typeId);
    //修改
    Integer updTypeInfo(AppraisalTypeInfoBO appraisalTypeInfoBO);
    //添加
    Integer addTypeInfo(AppraisalTypeInfoBO appraisalTypeInfoBO);
}
