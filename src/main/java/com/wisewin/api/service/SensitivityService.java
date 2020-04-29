package com.wisewin.api.service;


import com.wisewin.api.dao.SensitivityDAO;
import com.wisewin.api.dao.VersionsDAO;
import com.wisewin.api.entity.bo.SensitivityBO;
import com.wisewin.api.entity.bo.VersionsBO;
import com.wisewin.api.util.redisUtils.RedissonHandler;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("SensitivityService")
@Transactional
public class SensitivityService {

    @Resource
    private SensitivityDAO sensitivityDAO;

    /**
     * 添加
     * */
    public void add(String str){
        sensitivityDAO.add(str);
        deletRedis();
    }

    /**
     * 查询
     */
    public List<SensitivityBO> query(Integer pageNo,Integer pageSize){
        return  sensitivityDAO.query(pageNo,pageSize);
    }

    /**
     * 查询数量
     * */
    public Integer selectCount(){
      return   sensitivityDAO.selectCount();
    }

    /**
     * 删除
     */
    public void delete(Integer vid){
        sensitivityDAO.delete(vid);
        deletRedis();
    }

    private void deletRedis(){
        RedissonHandler.getInstance().delete("sensitivity");
    }

}
