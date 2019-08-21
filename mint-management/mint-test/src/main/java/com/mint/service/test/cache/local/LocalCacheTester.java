package com.mint.service.test.cache.local;

import java.util.concurrent.TimeUnit;

import com.mint.service.cache.exception.LocalCacheException;
import com.mint.service.cache.support.CacheOperator;
import com.mint.service.cache.support.local.LocalCacheFactory;
import com.mint.service.cache.support.local.LocalCacheType;

public class LocalCacheTester {
	
	public static void main(String[] args) throws LocalCacheException, InterruptedException {
		CacheOperator<String, Object> cache = LocalCacheFactory.create("test", LocalCacheType.MEMORY, 15L, TimeUnit.SECONDS, 10240L);
		cache.store("testKey", 123, null, null);
		Thread.sleep(3000L);
		int i = (int) cache.getByKey("testKey");
		System.out.println("Got value from cache: " + i);
		Thread.sleep(13000L);
		System.out.println("After expiration, cached value = " + cache.getByKey("testKey"));
	}
	
}
