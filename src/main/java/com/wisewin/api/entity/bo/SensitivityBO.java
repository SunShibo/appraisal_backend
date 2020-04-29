package com.wisewin.api.entity.bo;



import com.wisewin.api.common.base.BaseModel;

/**
 * 敏感字
 */
public class SensitivityBO extends BaseModel {
    private Integer id; //id
    private String str; //字

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
