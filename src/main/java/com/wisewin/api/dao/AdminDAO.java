package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.MenuBO;
import com.wisewin.api.entity.bo.RoleBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminDAO {
    /**
     * 通过手机号查找管理员信息
     * @param mobile
     * @return UserDO
     */
    AdminBO queryAdminInfoByMobile(String mobile);

    /**
     * 注册管理员信息
     * @param admin
     * @return
     */
    int adminRegister(AdminBO admin);

    /**
     * 判断角色名是否注册过
     * @param roleName 角色名称
     * @return
     */
    Integer selectCountByRoleName(String roleName);

    /**
     * 查找用户手机号是否注册过
     * @param mobile
     * @return
     */
    int selectCountByMobile(String mobile);

    /**
     * 添加角色信息
     * @param roleBO
     * @return 返回注册后的id
     */
    Integer addRole(RoleBO roleBO);

    /**
     * 查询所有角色
     * @return 所有角色
     */
    List<RoleBO> getRoleList(String status);

    /**
     * 根据角色id查询菜单信息
     * @return 所有权限
     */
    List<MenuBO> getMenuByRoleId(Integer roleId);


    /**
     * 向角色权限表中添加数据
     * @param roleId 角色id
     * @param menuIdArr 菜单id数组
     * @return
     */
    int addRoleMenu(@Param("roleId") Integer roleId, @Param("menuIdArr") String[] menuIdArr);

    /**
     * 根据角色姓名 查询角色信息
     * 角色姓名为null 查询所有
     * @param roleName
     * @return
     */
    List<RoleBO> getRoleByName(@Param("roleName") String roleName);

    /**
     * 根据角色id 查询角色信息
     * @param roleId
     * @return
     */
    RoleBO getRoleById(@Param("roleId") Integer roleId);

    /**
     * 根据角色id删除角色信息
     * @param roleIds 角色id
     */
    boolean delRoleById(@Param("roleIds") Integer[] roleIds);

    /**
     * 批量删除
     * 根据用户id删除用户信息
     * @param idArr 用户id
     * @return
     */
    boolean delAdminById(@Param("idArr") Integer[] idArr);

    /**
     * 根据角色id删除对应的所有权限
     * @param roleId
     */
    void delRoleMenuByRoleId(Integer roleId);


    //查询角色下是否有用户
    int checkRoleUser(Integer roleId);

    /**
     * wy
     * 根据角色id修改角色信息
     * @param roleId
     * @param roleName
     */
    void updateRoleNameByRoleId(@Param("roleId") Integer roleId, @Param("roleName") String roleName);

    /**
     * wy
     * 修改admin用户信息
     * @param adminBO 修改信息
     */
    Integer updateAdminUser(AdminBO adminBO);

    /**
     * 根据条件分页查询用户信息
     * wy
     * userId 用户id
     * roleId 角色id
     * userName 用户名
     * @return
     */
    List<AdminBO> getAdminByCond(Map<String, Object> map);

    /**
     * 根据条件查询用户信息总数
     * userId 用户id
     * roleId 角色id
     * userName 用户名
     * @return
     */
    Integer getAdminCountByCond(Map<String, Object> map);

    //获取所有菜单权限
    List<MenuBO> getMenuList();

}
