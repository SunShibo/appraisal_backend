package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

public class StatementBO extends BaseModel {
    private Integer commentSum;//评论数
    private Integer appraisalSum;//物品数
    private Integer registrationSum;//注册数
    private Integer activeSum;//活跃数


    public Integer getAppraisalSum() {
        return appraisalSum;
    }

    public void setAppraisalSum(Integer appraisalSum) {
        this.appraisalSum = appraisalSum;
    }

    public Integer getRegistrationSum() {
        return registrationSum;
    }

    public void setRegistrationSum(Integer registrationSum) {
        this.registrationSum = registrationSum;
    }

    public Integer getActiveSum() {
        return activeSum;
    }

    public void setActiveSum(Integer activeSum) {
        this.activeSum = activeSum;
    }

    public Integer getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(Integer commentSum) {
        this.commentSum = commentSum;
    }
}
