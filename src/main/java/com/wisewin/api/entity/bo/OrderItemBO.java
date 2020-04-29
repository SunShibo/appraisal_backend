package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItemBO extends BaseModel {
    private Integer id;//订单信息表
    private String orderNumber;//订单号
    private Integer userId;//用户id
    private BigDecimal payment=new BigDecimal(0);//支付金额
    private Integer state;//订单状态0-已取消 10-已付款
    private Integer rewardId;//被打赏人id
    private BigDecimal rewardAmount=new BigDecimal(0);//被打赏人实际收到金额
    private Integer commentId;//评论id
    private Integer appraisalId;//鉴定id
    private Integer payPlatform;//支付方式
    private String platformNumber;//支付流水号
    private String paymentTime;//支付时间
    private String closeTime;//交易完成时间
    private String createTime;//创建时间
    private String updateTime;//更新时间

    private String name;//被打赏人昵称
    private String phone;//被打赏人手机号
    private String rewardName;//被打赏人昵称
    private String rewardPhone;//被打赏人手机号
    private String typeName;//物品分类名称
    private String title;//物品标题

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRewardPhone() {
        return rewardPhone;
    }

    public void setRewardPhone(String rewardPhone) {
        this.rewardPhone = rewardPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getAppraisalId() {
        return appraisalId;
    }

    public void setAppraisalId(Integer appraisalId) {
        this.appraisalId = appraisalId;
    }

    public Integer getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(Integer payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        int index=closeTime.indexOf(".");
        if(index>0){
            closeTime=closeTime.substring(0,index);
        }
        this.closeTime = closeTime;

    }

    public String getCreateTime() {

        return createTime;
    }

    public void setCreateTime(String createTime) {
        int index=createTime.indexOf(".");
        if(index>0){
            createTime=createTime.substring(0,index);
        }
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        int index=updateTime.indexOf(".");
        if(index>0){
            updateTime=updateTime.substring(0,index);
        }
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
