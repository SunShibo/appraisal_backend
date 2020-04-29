package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.TopSearchBO;

import java.util.List;

public interface TopSearchDAO {
    //获取热门搜索
    List<TopSearchBO> getTopSearch();

}
