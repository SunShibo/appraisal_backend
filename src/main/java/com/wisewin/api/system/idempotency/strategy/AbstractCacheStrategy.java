package com.wisewin.api.system.idempotency.strategy;

import com.wisewin.api.system.idempotency.Null;
import com.wisewin.api.system.idempotency.core.Idempotent;
import com.wisewin.api.system.idempotency.invoke.InvokeContext;
import com.wisewin.api.util.redisUtils.RedissonHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 带缓存幂等策略
 * 
 * @author sunshibo
 */
public abstract class AbstractCacheStrategy implements IdempotentStrategy {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected String cachePrefix = "idem:rv:"; //
	// Idempotence:returnValue
	protected int cacheExpire = 1000 * 60 * 10; // 10 minute

	@Override
	public Object before(InvokeContext invokeContext) throws Throwable {
		String xid = invokeContext.getXid();
		System.out.println("before xid:" + xid);
		Object returnValue = RedissonHandler.getInstance().get(getCacheKey(xid)) ;
		logger.debug("get cache {}", returnValue);

		return returnValue;
	}

	@Override
	public void afterReturning(InvokeContext invokeContext, Object returnValue) throws Throwable {
		System.out.println("afterReturn .......");
		Idempotent idempotence = invokeContext.getInvokeMeta().getIdempotenceAnnotation();
		long _cacheExpire = (idempotence != null ? idempotence.cacheExpire() : cacheExpire);

		String xid = invokeContext.getXid();
		String cacheKey = getCacheKey(xid);
		Object cacheValue = returnValue == null ? Null.INSTANCE : returnValue;
		RedissonHandler.getInstance().set(cacheKey , cacheValue  , _cacheExpire) ;
		logger.debug("set cache {} = {}", cacheKey, cacheValue);
	}
	
	@Override
	public void afterThrowing(InvokeContext invokeContext, Exception e) throws Throwable {
//		throw e;
		System.out.println("afterThrowing");
	}
	
	@Override
	public void after(InvokeContext invokeContext) {
		
	}
	
	protected String getCacheKey(String xid) {
		return cachePrefix + xid;
	}
	
	public String getCachePrefix() {
		return cachePrefix;
	}

	public void setCachePrefix(String cachePrefix) {
		this.cachePrefix = cachePrefix;
	}

	public int getCacheExpire() {
		return cacheExpire;
	}

	public void setCacheExpire(int cacheExpire) {
		this.cacheExpire = cacheExpire;
	}


}