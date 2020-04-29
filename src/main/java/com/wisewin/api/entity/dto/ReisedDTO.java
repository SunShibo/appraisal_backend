package com.wisewin.api.entity.dto;

import java.util.Date;

/**
 * @Author: Wang bin
 * @date: Created in 11:11 2019/9/9
 */
public class ReisedDTO{
    private Integer id;
    private Integer appraisalId;
    private Integer commentId;
    private String content;
    private Integer userId;
    private String status;
    private String createTime;
    private String updateTime;
    private String apShow;
    private Integer apRead;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppraisalId() {
        return appraisalId;
    }

    public void setAppraisalId(Integer appraisalId) {
        this.appraisalId = appraisalId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getApShow() {
        return apShow;
    }

    public void setApShow(String apShow) {
        this.apShow = apShow;
    }

    public Integer getApRead() {
        return apRead;
    }

    public void setApRead(Integer apRead) {
        this.apRead = apRead;
    }
}
