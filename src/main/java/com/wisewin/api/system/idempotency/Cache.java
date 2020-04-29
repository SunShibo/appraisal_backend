package com.wisewin.api.system.idempotency;

/**
 * 缓存接口
 * 
 * @author sunshibo
 *
 */
public interface Cache {

	Object get(String key);

	int setnx(String key, Object value, long expire);

	void del(String key);

}
