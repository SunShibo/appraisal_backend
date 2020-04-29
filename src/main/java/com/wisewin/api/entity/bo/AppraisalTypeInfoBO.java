package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

import java.util.Date;


public class AppraisalTypeInfoBO extends BaseModel {

    private Integer id;
    private String name ;
    private Integer typeId;
    private Integer sort;
    private Date createTime ;
    private Date updateTime ;
    private String apShow ;
    private String pictureUrl ;


    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        this.apShow = apShow;
    }



}