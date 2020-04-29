package com.wisewin.api.system.idempotency.strategy;

import com.wisewin.api.system.idempotency.core.Idempotent;
import com.wisewin.api.system.idempotency.invoke.InvokeContext;
import com.wisewin.api.util.redisUtils.RedisLock;

/**
 * 锁等待策略
 * cache + lock + client retry
 *
 * @author Shibo Sun
 */
public class LockWaitStrategy extends AbstractCacheStrategy {

    public static final String CONTEXT_ATTR_LOCKKEY = "lockKey";

    private String lockPrefix = "idem:lk:"; // Idempotence:Lock
    private long lockTimeout = 1000 * 10; // 10 second
    private RedisLock lock = new RedisLock();

    @Override
    public String getName() {
        return "LockWait";
    }

    @Override
    public Object before(InvokeContext invokeContext) throws Throwable {
        Object returnValue = super.before(invokeContext);
        if (returnValue != null) {
            return returnValue;
        }

        String xid = invokeContext.getXid();
        String lockKey = getLockKey(xid);

        Idempotent idempotence = invokeContext.getInvokeMeta().getIdempotenceAnnotation();
        long _lockTimeout = (idempotence != null ? idempotence.timeout() : lockTimeout);
        if (!lock.tryLock(lockKey, _lockTimeout)) {
            throw new LockTimeoutException();
        }
        invokeContext.setAttr(CONTEXT_ATTR_LOCKKEY, lockKey); // lock acquired
        logger.debug("lock acquired {}", lockKey);

        returnValue = super.before(invokeContext);

        return returnValue;
    }

    @Override
    public void after(InvokeContext invokeContext) {
        String lockKey = (String) invokeContext.getAttr(CONTEXT_ATTR_LOCKKEY);
        if (lockKey != null) {
            lock.releaseLock(lockKey);
            logger.debug("release lock {}", lockKey);
        }
    }

    protected String getLockKey(String xid) {
        return lockPrefix + xid;
    }

    public String getLockPrefix() {
        return lockPrefix;
    }

    public void setLockPrefix(String lockPrefix) {
        this.lockPrefix = lockPrefix;
    }

    public long getLockTimeout() {
        return lockTimeout;
    }

    public void setLockTimeout(long lockTimeout) {
        this.lockTimeout = lockTimeout;
    }

    public RedisLock getLock() {
        return lock;
    }

    public void setLock(RedisLock lock) {
        this.lock = lock;
    }

    public static class LockTimeoutException extends RuntimeException {
        public LockTimeoutException() {
            super("To acquire the lock timeout");
        }
    }
}