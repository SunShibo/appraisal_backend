package com.wisewin.api.system.idempotency.strategy;

/**
 * 仅缓存策略
 * cache + server control
 * 
 * @author sunshibo
 */
public class OnlyCacheStrategy extends AbstractCacheStrategy {

	@Override
	public String getName() {
		return "OnlyCache";
	}
}