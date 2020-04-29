package com.wisewin.api.service;


import com.wisewin.api.common.constants.LanguageTalentConstants;
import com.wisewin.api.dao.AdminDAO;
import com.wisewin.api.entity.bo.AdminBO;
import com.wisewin.api.entity.bo.MenuBO;
import com.wisewin.api.entity.bo.RoleBO;
import com.wisewin.api.util.JsonUtils;
import com.wisewin.api.util.MD5Util;
import com.wisewin.api.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("AdminService")
@Transactional
public class AdminService {

    @Resource
    private AdminDAO adminDAO;

    /**
     * 根据手机号查找管理员信息
     * 管理员登录
     *
     * @param mobile
     * @return
     */
    public AdminBO queryAdminInfoByMobile(String mobile) {
        AdminBO adminBO = adminDAO.queryAdminInfoByMobile(mobile);
        if (adminBO != null) {
            List<MenuBO> urls = adminDAO.getMenuByRoleId(adminBO.getRoleId());
            Set<String> set = new HashSet<String>();
            if (urls != null && urls.size() > 0) {
                for (MenuBO menuBO : urls) {
                    if (menuBO.getUrl() != null && !StringUtils.isEmpty(menuBO.getUrl())) {
                        String[] split = menuBO.getUrl().split(",");
                        for (String str : split) {
                            set.add(str);
                        }
                    }
                }
            }
            adminBO.setUrl(set);
        }

        return adminBO;
    }


    /**
     * 根据当前登陆用户的角色id查询对应的权限
     *
     * @param roleId 角色id
     * @return 登陆用户对应的权限
     */
    public List<MenuBO> getRoleMenuSuccess(Integer roleId) {
        List<MenuBO> menus = adminDAO.getMenuByRoleId(roleId);
        if(menus!=null && menus.size()>0) {
            for (int i = 0; i < menus.size(); i++) {
                MenuBO menuBO = menus.get(i);
                for(int x=0;x<menus.size();x++){
                    if(menuBO.getPid().equals(menus.get(x).getId())){
                        menus.get(x).getCh().add(menuBO);
                        menus.remove(i);
                        --i;
                        break;
                    }
                }
            }
        }
        return  menus;
    }

    //获取所有菜单权限
    public List<MenuBO> getMenuList(){
        List<MenuBO> menuBOList=adminDAO.getMenuList();
        if(menuBOList!=null && menuBOList.size()>0) {
            for (int i = 0; i < menuBOList.size(); i++) {
                MenuBO menuBO = menuBOList.get(i);
                for(int x=0;x<menuBOList.size();x++){
                    if(menuBO.getPid().equals(menuBOList.get(x).getId())){
                        menuBOList.get(x).getCh().add(menuBO);
                        menuBOList.remove(i);
                        --i;
                        break;
                    }
                }
            }
        }
        return menuBOList;
    }

    /**
     * 管理员注册
     *
     * @param admin
     * @return
     */
    public int adminRegister(AdminBO admin) {
        //初始化管理员状态
        admin.setStatus(LanguageTalentConstants.NORMAL);// 状态 normal:正常  logout：注销
        //加密密码
        admin.setPassword(MD5Util.digest(admin.getPassword()));
        return adminDAO.adminRegister(admin);
    }


    /**
     * 判断角色名称是否注册过
     *
     * @param roleName
     * @return
     */
    public Integer selectCountByRoleName(String roleName) {
        return adminDAO.selectCountByRoleName(roleName);
    }

    /**
     * 查找用户名是否注册过
     *
     * @param name
     * @return
     */
    public int selectCountByName(String name) {
        return adminDAO.selectCountByMobile(name);
    }

    /**
     * 查找手机号是否注册过
     *
     * @param mobile
     * @return
     */
    public int selectCountByMobile(String mobile) {
        return adminDAO.selectCountByMobile(mobile);
    }

    /**
     * 添加角色信息
     *
     * @param roleBO
     * @return 返回角色id
     */
    public Integer addRole(RoleBO roleBO) {
        return adminDAO.addRole(roleBO);
    }

    /**
     * wy查询所有的角色信息
     *
     * @return 角色集合
     */
    public List<RoleBO> getRoleList(String status) {
        return adminDAO.getRoleList(status);
    }

    /**
     * 查询角色下是否有用户
     */
    public Integer checkRoleUser(String roleIds) {
        Integer[] idArr = JsonUtils.getIntegerArray4Json(roleIds);
        for (int i = 0; i < idArr.length; i++) {
            int count = adminDAO.checkRoleUser(new Integer(idArr[i]));
            if (count > 0) {
                return idArr[i];
            }
        }
        return null;
    }

    /**
     * 根据条件分页查询用户信息
     * wy
     * userId 用户id
     * roleId 角色id
     * userName 用户名
     *
     * @return
     */
    public List<AdminBO> getAdminByCond(Map<String, Object> map) {
        return adminDAO.getAdminByCond(map);
    }

    /**
     * 根据条件查询用户信息总数量
     * wy
     * userId 用户id
     * roleId 角色id
     * userName 用户名
     *
     * @return
     */
    public Integer getAdminCountByCond(Map<String, Object> map) {
        return adminDAO.getAdminCountByCond(map);
    }

    /**
     * wy
     * 根据角色id查询角色信息
     *
     * @param roleId
     * @return
     */
    public RoleBO getRoleById(Integer roleId) {
        return adminDAO.getRoleById(roleId);
    }

    /**
     * 向角色权限表中添加数据
     *
     * @param roleId    角色id
     * @param menuIdArr 菜单id数组
     * @return
     */
    public int addRoleMenu(Integer roleId, String[] menuIdArr) {
        return adminDAO.addRoleMenu(roleId, menuIdArr);
    }

    /**
     * 根据角色id删除角色
     *
     * @param roleIds 角色id
     */
    public boolean delRoleById(Integer[] roleIds) {
        return adminDAO.delRoleById(roleIds);
    }


    /**
     * 修改一个角色的菜单权限信息
     *
     * @param roleBO    这个角色的信息
     * @param menuIdArr 菜单id
     */
    public boolean updRoleToMenu(RoleBO roleBO, String menuIdArr) {
        String[] menuIds = {};
        if(menuIdArr!=null){
            menuIds=menuIdArr.split(",");
        }
        //删除这个角色所有权限
        adminDAO.delRoleMenuByRoleId(roleBO.getId());
        //添加权限
        return adminDAO.addRoleMenu(roleBO.getId(), menuIds) > 0;
    }
    /**
     * wy根据用户id删除用户信息
     *
     * @param idArr
     * @return
     */
    public boolean delAdminById(Integer[] idArr) {
        return adminDAO.delAdminById(idArr);
    }

    /**
     * wy
     * 修改admin用户信息
     *
     * @param param 用户信息
     * @return 是否修改成功
     */
    public boolean updateAdminUser(AdminBO param) {
        //加密密码
        if(!StringUtils.isEmpty(param.getPassword())) {
            param.setPassword(MD5Util.digest(param.getPassword()));
        }
        return adminDAO.updateAdminUser(param) > 0;
    }

    /**
     * 根据角色id修改角色名称
     *
     * @param roleId   角色id
     * @param roleName 角色名称
     */
    public void updateRoleNameByRoleId(Integer roleId, String roleName) {
        adminDAO.updateRoleNameByRoleId(roleId, roleName);
    }

    /**
     * 查询角色对应的菜单
     * @param roleName 角色名称 为null查询所有
     * @return
     */
    public List<RoleBO> getRoleByRoleName(String roleName) {
        //查询所有角色
        List<RoleBO> roleList = adminDAO.getRoleByName(roleName);

        //循环查询角色拥有的菜单
        for (RoleBO role : roleList) {
            List<Integer> menuIds = new ArrayList<Integer>();// 存放权限id
            List<String> menuNames = new ArrayList<String>(); // 存放权限name
            List<MenuBO> menus = adminDAO.getMenuByRoleId(role.getId());
            if(menus!=null && menus.size()>0) {
                for (int i = 0; i < menus.size(); i++) {
                    MenuBO menuBO = menus.get(i);
                    if(menuBO.getPid()!=0){
                        menuIds.add(menuBO.getId());
                    }
                    menuNames.add(menuBO.getMenuName());
                    for(int x=0;x<menus.size();x++){
                        if(menuBO.getPid().equals(menus.get(x).getId())){
                            menus.get(x).getCh().add(menuBO);
                            menus.remove(i);
                            --i;
                            break;
                        }
                    }
                }
            }
            role.setMenuBOS(menus);
            role.setMenuIds(menuIds);
            role.setMenuNames(menuNames);
        }

        return roleList;
    }

}
