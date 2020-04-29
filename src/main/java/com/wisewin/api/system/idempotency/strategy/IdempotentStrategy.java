package com.wisewin.api.system.idempotency.strategy;

import com.wisewin.api.system.idempotency.invoke.InvokeContext;

/**
 * 幂等策略接口
 * 
 * @author sunshibo
 */
public interface IdempotentStrategy {
	
	/**
	 * 策略名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 方法执行前处理
	 * 
	 * @param invokeContext
	 * @return
	 * @throws Throwable
	 */
	Object before(InvokeContext invokeContext) throws Throwable;

	/**
	 * 方法正常返回后处理
	 * 
	 * @param invokeContext
	 * @param returnValue
	 * @throws Throwable
	 */
	void afterReturning(InvokeContext invokeContext, Object returnValue) throws Throwable;

	/**
	 * 方法执行出现异常时处理
	 * 
	 * @param invokeContext
	 * @param e
	 * @throws Throwable
	 */
	void afterThrowing(InvokeContext invokeContext, Exception e) throws Throwable;
	
	/**
	 * 方法执行结束后处理（finally)
	 * 
	 * @param invokeContext
	 */
	void after(InvokeContext invokeContext);
}