package com.wisewin.api.entity.bo;

import com.wisewin.api.common.base.BaseModel;

public class FormUtilBO extends BaseModel {
    private int day;
    private  int month;
    private  int year;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
