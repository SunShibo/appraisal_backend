package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

import java.util.Date;

public class AppraisalTypeBo extends BaseModel {
    private Integer id;

    private String typeName;

    private Date createTime;

    private Date updateTime;

    private String apShow;

    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
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

    public String getApShow() {
        return apShow;
    }

    public void setApShow(String apShow) {
        this.apShow = apShow == null ? null : apShow.trim();
    }

    @Override
    public String toString() {
        return "AppraisalTypeBo{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", apShow='" + apShow + '\'' +
                '}';
    }
}