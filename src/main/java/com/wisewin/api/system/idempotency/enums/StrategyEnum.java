package com.wisewin.api.system.idempotency.enums;

/**
 * 策略的枚举类
 * Created by Shibo on 16/12/28.
 */
public enum StrategyEnum  {

    ONLY_CACHE("OnlyCache") , LOCK_WAIT("LockWait") ;

    StrategyEnum(String value) {
        this.value = value;
    }

    private String value ;

    public String getValue() {
        return value;
    }
}
