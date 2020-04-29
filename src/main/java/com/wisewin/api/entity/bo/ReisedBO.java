package com.wisewin.api.entity.bo;

import java.util.Date;

/**
 * @Author: Wang bin
 * @date: Created in 10:52 2019/9/9
 */
public class ReisedBO {
    private Integer id;
    private Integer appraisalId;
    private Integer commentId;
    private String content;
    private Integer userId;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String apShow;
    private Integer apRead;
    private String judge;

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

    public Integer getApRead() {
        return apRead;
    }

    public void setApRead(Integer apRead) {
        this.apRead = apRead;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }
}
