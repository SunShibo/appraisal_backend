package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.GradeBO;

import java.util.List;

public interface GradeDao {
    Integer addGrade(GradeBO gradeBO);

    Integer delGrade(Integer id);

    Integer updGrade(GradeBO gradeBo);

    List<GradeBO> getGradeBOList();

    /**
     * 获取等级列表
     * @return
     */
    List<GradeBO> queryGradeList();
}
