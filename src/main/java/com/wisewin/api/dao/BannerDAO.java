package com.wisewin.api.dao;

import com.wisewin.api.entity.bo.BannerBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDAO {
    //查询banner
    List<BannerBO> getBanner(@Param("state") String state);

    //修改bannergetBanner
    Integer updBannerBO(BannerBO bannerBO);

    //添加
    Integer addBannerBO(BannerBO bannerBO);

    //删除
    Integer delBanner(@Param("id") Integer id);

    //获取单个
    BannerBO getBannerById(@Param("id")Integer id);
}
