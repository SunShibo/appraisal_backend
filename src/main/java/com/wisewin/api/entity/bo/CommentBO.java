package com.wisewin.api.entity.bo;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @Author: Wang bin
 * @date: Created in 14:55 2019/10/8
 */
public class CommentBO {
    private Integer id;
    private Integer appraisalId;
    private Integer userId;
    private String cnComment;
    private String goodsState;
    private String judge;
    private String cmShow;
    private Date createTime;
    private Date updateTime;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCnComment() {
        return cnComment;
    }

    public void setCnComment(String cnComment) {
        this.cnComment = cnComment;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getCmShow() {
        return cmShow;
    }

    public void setCmShow(String cmShow) {
        this.cmShow = cmShow;
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

    @Override
    public String toString() {
        return "CommentBO{" +
                "id=" + id +
                ", appraisalId=" + appraisalId +
                ", userId=" + userId +
                ", cnComment='" + cnComment + '\'' +
                ", goodsState='" + goodsState + '\'' +
                ", judge='" + judge + '\'' +
                ", cmShow='" + cmShow + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
