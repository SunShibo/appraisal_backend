package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.UserBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //根据条件查询用户列表
    List<UserBO> getUserList(@Param("watermarkState")String watermarkState,
            @Param("phone") String phone,@Param("name") String name,@Param("pageOffset") Integer pageOffset,@Param("pageSize")Integer pageSize);
    List<UserBO> querySystemUser();

    Integer getUserListCount(@Param("watermarkState")String watermarkState,@Param("phone") String phone,@Param("name") String name);

    //根据id查询用户详情
    UserBO getUserById(@Param("id")Integer id);

    //修改用户账号状态
    Integer updUser(UserBO userBO);
    //修改用户账号金额
    Integer updUserMoney(UserBO userBO);

    int updatewatermarkState(@Param("id")Integer id, @Param("watermarkState")String watermarkState);



}
