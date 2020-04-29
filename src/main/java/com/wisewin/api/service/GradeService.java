package com.wisewin.api.service;

import com.wisewin.api.dao.GradeDao;
import com.wisewin.api.entity.bo.GradeBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class GradeService {
    @Resource
    GradeDao gradeDao;
    public Integer addGrade(GradeBO gradeBO){
        return gradeDao.addGrade(gradeBO);
    }

    public Integer delGrade(Integer id){
        return gradeDao.delGrade(id);
    }

    public Integer updGrade(GradeBO gradeBo){
        return gradeDao.updGrade(gradeBo);
    }

    public List<GradeBO> getGradeBOList(){
        return gradeDao.getGradeBOList();
    }
}
