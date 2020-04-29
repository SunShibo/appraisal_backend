package com.wisewin.api.service;

import com.wisewin.api.dao.WithdrawDAO;
import com.wisewin.api.entity.bo.WithdrawBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class WithdrawService {

    @Resource
    WithdrawDAO withdrawDAO;

    public List<WithdrawBO> getWithdrawBOList(Integer pageOffset, Integer pageSize, Date afterTime, Date beforeTime, String type) {
       return withdrawDAO.getWithdrawBOList(pageOffset,pageSize,afterTime,beforeTime,type);
    }

    public WithdrawBO getWithdrawById(Integer id){
        return withdrawDAO.getWithdrawById(id);
    }

    public Integer getWithdrawCount(Date afterTime,Date beforeTime,String type){
        return withdrawDAO.getWithdrawCount(afterTime,beforeTime,type);
    }

    public Integer updWithdrawBO(WithdrawBO withdrawBO){
        return withdrawDAO.updWithdrawBO(withdrawBO);
    }


}