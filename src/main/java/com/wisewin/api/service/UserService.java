package com.wisewin.api.service;

import com.wisewin.api.dao.GradeDao;
import com.wisewin.api.dao.UserDao;
import com.wisewin.api.entity.bo.GradeBO;
import com.wisewin.api.entity.bo.UserBO;
import com.wisewin.api.entity.common.constants.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class UserService {


    @Autowired
    private GradeDao gradeDAO;

    /**
     * 获取用户等级
     * @param
     * @return
     */
    private String isGrade(List<GradeBO> gradeBOS, Integer integral){
        for (GradeBO gradeBO : gradeBOS) {
            if(integral <= gradeBO.getEmpirical()){
                return gradeBO.getGradeName();
            }
        }
        return "未知等级";
    }
    
    @Resource
    UserDao userDao;
    //根据条件查询用户列表
    public List<UserBO> getUserList(String watermarkState,String phone,String name,Integer pageOffset,Integer pageSize){
        List<GradeBO> gradeBOS = gradeDAO.queryGradeList();
        List<UserBO> userList = userDao.getUserList(watermarkState, phone, name, pageOffset, pageSize);
        for (UserBO userBO : userList) {
            userBO.setLevel(isGrade(gradeBOS,userBO.getIntegral()));
        }
        return userList;
    }
    public List<UserBO> querySystemUser(){
        List<UserBO> userList = userDao.querySystemUser();
        return userList;
    }
    //根据条件查询用户列表
    public Integer getUserListCount(String watermarkState,String phone,String name){
        return userDao.getUserListCount(watermarkState,phone,name);
    }

    //根据id查询用户详情
    public UserBO getUserById(Integer id){
        return userDao.getUserById(id);
    }

    //修改用户账号状态
    public Integer updUser(UserBO userBO){
        return userDao.updUser(userBO);
    }

    public Integer updUserMoney(UserBO userBO){
        return userDao.updUserMoney(userBO);
    }

    /**
     * 修改水印状态
     * @param id
     * @param watermarkState
     * @return
     */
    public Result updatewatermarkState(Integer id, String watermarkState){
        int i = userDao.updatewatermarkState(id, watermarkState);
        if(i > 0){
            return new Result("","0000000","成功");
        }
        return new Result("","0000002","失败");
    }
}
