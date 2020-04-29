package com.wisewin.api.entity.bo;

import java.util.Date;

/**
 * @Author: Wang bin
 * @date: Created in 10:19 2019/9/17
 */
public class GradeBO {
    private Integer id;
    private Integer empirical;
    private Integer empiricalEnd;
    private String gradeName;
    private String apShow;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpirical() {
        return empirical;
    }

    public void setEmpirical(Integer empirical) {
        this.empirical = empirical;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getApShow() {
        return apShow;
    }

    public void setApShow(String apShow) {
        this.apShow = apShow;
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

    public Integer getEmpiricalEnd() {
        return empiricalEnd;
    }

    public void setEmpiricalEnd(Integer empiricalEnd) {
        this.empiricalEnd = empiricalEnd;
    }
}
