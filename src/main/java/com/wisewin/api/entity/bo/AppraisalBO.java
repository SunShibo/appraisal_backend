package com.wisewin.api.entity.bo;

import java.util.Date;

/**
 * @Author: Wang bin
 * @date: Created in 17:22 2019/9/4
 */
public class AppraisalBO {
    private Integer id;
    private Integer userId;
    private String appraisalTypeName;
    private String title;
    private String describc;
    private String appraisalState;
    private Integer browse;
    private String apCase;
    private String status;
    private String headUrl;
    private Integer appraisalTypeId;
    private String apImages;
    private Date createTime;
    private Date updateTime;
    private String userName;
    private String reisedState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAppraisalTypeName() {
        return appraisalTypeName;
    }

    public void setAppraisalTypeName(String appraisalTypeName) {
        this.appraisalTypeName = appraisalTypeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribc() {
        return describc;
    }

    public void setDescribc(String describc) {
        this.describc = describc;
    }

    public String getAppraisalState() {
        return appraisalState;
    }

    public void setAppraisalState(String appraisalState) {
        this.appraisalState = appraisalState;
    }

    public Integer getBrowse() {
        return browse;
    }

    public void setBrowse(Integer browse) {
        this.browse = browse;
    }

    public String getApCase() {
        return apCase;
    }

    public void setApCase(String apCase) {
        this.apCase = apCase;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getApImages() {
        return apImages;
    }

    public void setApImages(String apImages) {
        this.apImages = apImages;
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

    public Integer getAppraisalTypeId() {
        return appraisalTypeId;
    }

    public void setAppraisalTypeId(Integer appraisalTypeId) {
        this.appraisalTypeId = appraisalTypeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReisedState() {
        return reisedState;
    }

    public void setReisedState(String reisedState) {
        this.reisedState = reisedState;
    }

    @Override
    public String toString() {
        return "AppraisalBO{" +
                "id=" + id +
                ", userId=" + userId +
                ", appraisalTypeName='" + appraisalTypeName + '\'' +
                ", title='" + title + '\'' +
                ", describc='" + describc + '\'' +
                ", appraisalState='" + appraisalState + '\'' +
                ", browse=" + browse +
                ", apCase='" + apCase + '\'' +
                ", status='" + status + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", appraisalTypeId=" + appraisalTypeId +
                ", apImages='" + apImages + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userName='" + userName + '\'' +
                ", reisedState='" + reisedState + '\'' +
                '}';
    }
}
