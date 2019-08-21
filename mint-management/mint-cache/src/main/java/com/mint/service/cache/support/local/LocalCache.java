package com.mint.service.cache.support.local;

import java.util.concurrent.TimeUnit;

import com.mint.service.cache.support.CacheOperator;

public interface LocalCache {

	LocalCacheType getType();
	
	CacheOperator<String, Object> getOperator(Long expire, TimeUnit unit, Long byteSize);
	
}
