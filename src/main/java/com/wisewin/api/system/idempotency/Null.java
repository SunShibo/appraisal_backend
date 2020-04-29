package com.wisewin.api.system.idempotency;

/**
 * 幂等方法返回值为null或返回类型为void， 都以Null.INSTANCE代替并存入缓存中
 * 
 * @author sunshibo
 */
public class Null {
	public static final Null INSTANCE = new Null();
}
