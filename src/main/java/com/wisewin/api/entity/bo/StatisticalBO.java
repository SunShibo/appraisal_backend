package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

//后台统计
public class StatisticalBO extends BaseModel {
    private Integer dayCount;//当日数
    private Integer monthCount;//当月数
    private Integer yearCount;//当年数
    private String typeName;//类型名字

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(Integer monthCount) {
        this.monthCount = monthCount;
    }

    public Integer getYearCount() {
        return yearCount;
    }

    public void setYearCount(Integer yearCount) {
        this.yearCount = yearCount;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
