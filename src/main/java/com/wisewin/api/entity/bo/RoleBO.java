package com.wisewin.api.entity.bo;

import java.util.Date;
import java.util.List;

public class RoleBO {
    private Integer id; // 角色

    private String roleName; //角色名称

    private Date createTime; //创建时间按

    private Date updateTime; // 修改时间

    private List<MenuBO> menuBOS;

    private MenuBO menuBO;
    private List<Integer> menuIds; // 角色对应的所有权限的ids

    private List<String> menuNames;// 角色对应的所有的权限名称

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

    public List<String> getMenuNames() {
        return menuNames;
    }

    public void setMenuNames(List<String> menuNames) {
        this.menuNames = menuNames;
    }

    public MenuBO getMenuBO() {
        return menuBO;
    }

    public void setMenuBO(MenuBO menuBO) {
        this.menuBO = menuBO;
    }

    public List<MenuBO> getMenuBOS() {
        return menuBOS;
    }

    public void setMenuBOS(List<MenuBO> menuBOS) {
        this.menuBOS = menuBOS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
