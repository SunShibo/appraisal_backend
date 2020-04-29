package com.wisewin.api.entity.dto;


import com.wisewin.api.common.base.BaseModel;

import java.util.Date;
import java.util.Set;

/**
 * Created by 王彬 on 2019/5/16.
 */
public class AcceptLogDTO extends BaseModel {

    private Integer id;
    private Date create_time;
    private Integer adminId;
    private Integer userId;
    private String type;
    private String content;


    public AcceptLogDTO() {

    }

    public AcceptLogDTO(Integer adminId, Integer usreId, String type, String content) {
        this.adminId = adminId;
        this.userId = usreId;
        this.type = type;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
