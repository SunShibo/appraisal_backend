package com.wisewin.api.system.idempotency.core;

import com.wisewin.api.system.idempotency.enums.StrategyEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Shibo on 16/8/29.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE })
public @interface Idempotent {

    /**
     * 幂等ID的参数位置下标,从0开始.
     */
    int idemIdIndex() default 0; // xidExpr = #args[0]

    /**
     * 超时时间；锁超时or业务处理超时
     */
    long timeout() default 1000 * 10; // 10 second; lock timeout / process timeout

    /**
     * 缓存过期时间
     */
    int cacheExpire() default 1000 * 60 * 10; // 10 minute

    /**
     * 幂等策略
     */
    StrategyEnum strategy() default StrategyEnum.LOCK_WAIT;
}
