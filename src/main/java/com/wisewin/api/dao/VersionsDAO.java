package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.VersionsBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 版本
 */
public interface VersionsDAO {

    /**
     * 添加版本
     * */
    Integer addVersions(VersionsBO versionsBO);

    /**
     * 查询版本
     */
    List<VersionsBO> queryVersions(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    /**
     * 查询分页数量
     * */
    Integer selectVersionBOCount();

    /**
     * 删除
     */
    Integer deleteVersions(Integer vid);


}
