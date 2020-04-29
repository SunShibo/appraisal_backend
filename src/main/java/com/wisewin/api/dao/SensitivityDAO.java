package com.wisewin.api.dao;


import com.wisewin.api.entity.bo.SensitivityBO;
import com.wisewin.api.entity.bo.VersionsBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 敏感字
 */
public interface SensitivityDAO {

    /**
     * 添加
     * */
    Integer add(@Param("str")String str);

    /**
     * 查询
     */
    List<SensitivityBO> query(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * */
    Integer selectCount();

    /**
     * 删除
     */
    Integer delete(Integer vid);


}
