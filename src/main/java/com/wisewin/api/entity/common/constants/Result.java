package com.wisewin.api.entity.common.constants;

import java.io.Serializable;

/**
 *  app 端要求返回的数据结构
 */
public class Result<T> implements Serializable {
    private String code ;
    private String msg ;
    private T data ;

    public Result() {
    }

    public Result(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
