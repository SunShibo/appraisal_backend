package com.wisewin.api.system.accessControl.interfaces;

/**
 * 权限控制核心与业务对接的接口
 * Created by Shibo on 17/1/2.
 */
public interface IAccessControlBusiness {

    /**
     * 更新数据库中的权限二进制值
     * @param userId
     * @param newStatus
     * @return
     */
    int updateNewStatus(int userId, int newStatus) ;
}
