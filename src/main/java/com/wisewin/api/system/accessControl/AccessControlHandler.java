package com.wisewin.api.system.accessControl;

import com.wisewin.api.system.accessControl.interfaces.IAccessControlBusiness;

import javax.annotation.Resource;

/**
 * 权限控制的核心
 * Created by Shibo on 17/1/2.
 */
public class AccessControlHandler {

    public static final int BIT_LENGTH = 32 ;

    @Resource
    IAccessControlBusiness accessControlBusiness ;

    /**
     * 设置权限
     * @param index
     * @param permit
     */
    public void setPermission (int userId , int originalValue, int index , boolean permit){
        if (index > BIT_LENGTH || index < 0 )
            throw new IllegalArgumentException("位长超出范围") ;

        int newValue = calculationBit(originalValue , index , permit) ;

        int result = accessControlBusiness.updateNewStatus(userId , newValue) ;
        if (result == 0)
            throw new RuntimeException("更新数据失败") ;

        synchronizeCache(userId , newValue) ;
    }

    /**
     * 检查权限
     * @param originalValue
     * @param index
     * @return
     */
    public boolean checkPermission(int originalValue , int index) {
        int tmp = 1 << index ;
        return (tmp & originalValue) > 0 ;
    }

    /**
     * 计算数值
     * @param originalValue
     * @param index
     * @param permit
     * @return
     */
    public int calculationBit(int originalValue , int index , boolean permit) {

        int tmp = 1 << index ;
        if (permit) {
            originalValue = originalValue | tmp ;
        } else {
            tmp = ~tmp ;
            originalValue = originalValue & tmp ;
        }
        return originalValue ;
    }

    /**
     * 同步缓存
     */
    private void synchronizeCache(int userId, int newValue) {

    }

    public static void main(String[] args) {
        String sb = "1000";

        int d = Integer.parseInt(sb.toString(), 2);
        System.out.println(d);
    }

}
