package com.wisewin.api.service;

import com.wisewin.api.dao.AppraisalTypeDAO;
import com.wisewin.api.entity.bo.AppraisalBO;
import com.wisewin.api.entity.bo.AppraisalTypeBo;
import com.wisewin.api.entity.bo.AppraisalTypeInfoBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AppraisalTypeService {
    @Resource
    AppraisalTypeDAO appraisalTypeDAO;

    /**
     * 获取鉴定类型列表
     * @return
     */
    public List<AppraisalTypeBo> getAppraisalType(){
        return appraisalTypeDAO.getAppraisalType();
    }

    //修改
    public Integer updAppraisalType(AppraisalTypeBo appraisalTypeBo){
        return appraisalTypeDAO.updAppraisalType(appraisalTypeBo);
    }
    //添加
    public Integer addAppraisalType(AppraisalTypeBo appraisalTypeBo){
        return appraisalTypeDAO.addAppraisalType(appraisalTypeBo);
    }




    /**
     * 获取鉴定类型详情列表
     * @return
     */
    public List<AppraisalTypeInfoBO> queryTypeInfo(Integer typeId){
        return appraisalTypeDAO.queryTypeInfo(typeId);
    }

    //修改
    public Integer updTypeInfo(AppraisalTypeInfoBO appraisalTypeBo){
        return appraisalTypeDAO.updTypeInfo(appraisalTypeBo);
    }
    //添加
    public Integer addTypeInfo(AppraisalTypeInfoBO appraisalTypeBo){
        return appraisalTypeDAO.addTypeInfo(appraisalTypeBo);
    }
}
