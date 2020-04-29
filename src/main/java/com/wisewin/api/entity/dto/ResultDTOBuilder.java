package com.wisewin.api.entity.dto;

import com.wisewin.api.util.StringUtils;
import com.wisewin.api.util.env.Env;

/**
 * Created by sunshibo on 2016/3/16.
 */
public class ResultDTOBuilder {

    public ResultDTOBuilder() {
    }

    public static <T> ResultDTO<T> success(T data) {
        ResultDTO<T> instance = getInstance("", "", true, data);
        return instance;
    }

    public static ResultDTO success(String errCode) {
        Env env = new Env();
        ResultDTO<String> instance = getInstance(errCode, StringUtils.clearNull(env.getProperty(errCode)), true, "");
        return instance;
    }


    public static <T> ResultDTO<T> failure(T data) {
        ResultDTO<T> instance = getInstance("", "", false, data);
        return instance;
    }

    public static ResultDTO failure(String errCode, String errMsg) {
        ResultDTO<String> instance = getInstance(errCode, errMsg, false, "");
        return instance;
    }



    public static ResultDTO failure(String errCode, String errMsg, String data) {
        ResultDTO<String> instance = getInstance(errCode, errMsg, false, data);
        return instance;
    }

    public static ResultDTO failure(String errCode) {
        Env env = new Env();
        ResultDTO<String> instance = getInstance(errCode, StringUtils.clearNull(env.getProperty(errCode)), false, "");
        return instance;
    }


    public static <T> ResultDTO<T> failures(String errCode, T date) {
        Env env = new Env();
        ResultDTO<T> instance = getInstance(errCode, StringUtils.clearNull(env.getProperty(errCode)), false, date);
        return instance;
    }

    public static <T> ResultDTO<T> getInstance(String errCode, String errMsg, boolean success, T data) {
        ResultDTO<T> resultDTO = new ResultDTO<T>();
        resultDTO.setData(data);
        resultDTO.setErrCode(errCode);
        resultDTO.setErrMsg(errMsg);
        resultDTO.setSuccess(success);
        return resultDTO;
    }
}
