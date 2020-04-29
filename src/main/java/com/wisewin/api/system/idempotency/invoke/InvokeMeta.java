package com.wisewin.api.system.idempotency.invoke;

import com.wisewin.api.system.idempotency.core.Idempotent;
import com.wisewin.api.system.idempotency.strategy.IdempotentStrategy;

import java.lang.reflect.Method;

/**
 * 方法执行元数据
 * 
 * @author sunshibo
 *
 */
public class InvokeMeta {

	private Method method;
	
	private String methodMd5;

	private IdempotentStrategy strategy;
	
	private Idempotent idempotenceAnnotation;
	
	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getMethodMd5() {
		return methodMd5;
	}

	public void setMethodMd5(String methodMd5) {
		this.methodMd5 = methodMd5;
	}

	public IdempotentStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(IdempotentStrategy strategy) {
		this.strategy = strategy;
	}

	public Idempotent getIdempotenceAnnotation() {
		return idempotenceAnnotation;
	}

	public void setIdempotenceAnnotation(Idempotent idempotenceAnnotation) {
		this.idempotenceAnnotation = idempotenceAnnotation;
	}
}