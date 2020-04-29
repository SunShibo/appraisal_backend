package com.wisewin.api.service;


import com.wisewin.api.dao.VersionsDAO;
import com.wisewin.api.entity.bo.VersionsBO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("VersionsService")
@Transactional
public class VersionsService {

    @Resource
    private VersionsDAO versionsDAO;

    /**
     * 添加版本
     */
    public boolean getaddVersions(VersionsBO versionsBO){
        return versionsDAO.addVersions(versionsBO)>0;
    }

    /**
     * 查询版本
     */
    public List<VersionsBO> getqueryVersions(Integer pageNo,Integer pageSize){
        return  versionsDAO.queryVersions(pageNo,pageSize);
    }

    /**
     * 查询分页数量
     * */
    public Integer selectVersionBOCount(){
        return versionsDAO.selectVersionBOCount();
    }

    /**
     * 删除
     */
    public boolean getdeleteVersions(Integer vid){
    return  versionsDAO.deleteVersions(vid)>0;
    }


}
