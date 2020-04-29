package com.wisewin.api.util.redisUtils;

import org.redisson.core.RLock;

import java.util.concurrent.TimeUnit;

/**
 * Created by Shibo on 16/11/21.
 */
public class RedisLock {

    /** 默认锁的过期时间 10分钟*/
    private final long expireTime = 60 * 10 ;

    private RLock rLock ;

    public void lock(String key) {
        RLock rLock = RedissonHandler.getInstance().getRLock(key, expireTime);
        rLock.lock();
    }

    public boolean tryLock(String key, long timeout) throws InterruptedException {
        RLock rLock = RedissonHandler.getInstance().getRLock(key, expireTime);
        return rLock.tryLock(timeout , TimeUnit.SECONDS );
    }

    public void releaseLock(String key) {
        RLock rLock = RedissonHandler.getInstance().getRLock(key, expireTime);
        rLock.unlock();
    }
}
