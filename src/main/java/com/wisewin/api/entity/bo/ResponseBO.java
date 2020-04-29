package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

public class ResponseBO extends BaseModel {
    private FormUtilBO commentSum; //总评论数
    private FormUtilBO publishSum; //商品发布数

    public FormUtilBO getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(FormUtilBO commentSum) {
        this.commentSum = commentSum;
    }

    public FormUtilBO getPublishSum() {
        return publishSum;
    }

    public void setPublishSum(FormUtilBO publishSum) {
        this.publishSum = publishSum;
    }
}
