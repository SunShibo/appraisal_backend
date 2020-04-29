package com.wisewin.api.entity.bo;

/**
 * @Author: Wang bin
 * @date: Created in 14:56 2019/9/30
 */
public class ComtBO {
    private Integer id;//评论表
    private Integer appraisalId;//鉴定id
    private Integer userId;//用户id
    private String cnComment;//内容
    private String goodsState;//状态
    private String cmShow;//显示
    private String createTime;
    private String updateTime;
    private String judge;
    private String name;//评论人姓名
    private String headUrl;//头像路径
    private Integer integral;//经验值


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

    public String getCmShow() {
        return cmShow;
    }

    public void setCmShow(String cmShow) {
        this.cmShow = cmShow;
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

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}
