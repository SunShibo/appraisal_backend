package com.wisewin.api.service;

import com.wisewin.api.dao.BannerDAO;
import com.wisewin.api.entity.bo.BannerBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class BannerService {
    @Resource
    BannerDAO bannerDAO;
    //查询banner
    public List<BannerBO> getBanner(String state){
        return bannerDAO.getBanner(state);
    }
    //修改banner
    public Integer updBannerBO(BannerBO bannerBO){
        return bannerDAO.updBannerBO(bannerBO);
    }
    //添加
    public Integer addBannerBO(BannerBO bannerBO){
        return bannerDAO.addBannerBO(bannerBO);
    }
    //删除
    public Integer delBanner(Integer id){
        return bannerDAO.delBanner(id);
    }
    //获取单个
    public BannerBO getBannerById(Integer id){
        return bannerDAO.getBannerById(id);
    }
}
