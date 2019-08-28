package com.mint.service.cache.support.local;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.cache.support.CacheOperator;
import com.mint.service.cache.support.local.impl.DefaultLocalMemoryCache;

public class LocalCacheFactory  {
	
	private static final Logger LOG = LoggerFactory.getLogger(LocalCacheFactory.class);
	
	private static LocalCache DISKCACHE;
	
	private static LocalCache MEMCACHE;
	
	static {
		// local cache must be implemented by SPI
		Collection<LocalCache> caches = CommonServiceLoader.getMultipleServices(LocalCache.class, null);
		if (caches != null && !caches.isEmpty()) {
			for (LocalCache c: caches) {
				if (LocalCacheType.DISK == c.getType()) {
					if (DISKCACHE == null) {
						DISKCACHE = c;
					}
				} else if (LocalCacheType.MEMORY == c.getType()) {
					if (MEMCACHE == null) {
						MEMCACHE = c;
					}
				} else {
					LOG.error("Unrecognized local cache type.");
				}
			}
		}
		if (DISKCACHE == null) {
			// TODO create default disk cache
		}
		if (MEMCACHE == null) {
			MEMCACHE = new DefaultLocalMemoryCache();
		}
	}
	
	private static final Map<String, CacheOperator<String, Object>> LOCALCACHE = Maps.newConcurrentMap();

	public static CacheOperator<String, Object> create(String key, LocalCacheType type, Long expireTime, TimeUnit unit, Long byteSize) throws MintException {
		if (LOCALCACHE.containsKey(key)) {
			throw MintException.getException(Error.ILLEGAL_PARAM_ERROR, null, null)
			.setMsg("Cannot allow dupplicate key for local cache");
		}
		CacheOperator<String, Object> cache = null;
		switch(type) {
		case DISK: 
//			cache = DISKCACHE.getOperator();
			throw MintException.getException(Error.ILLEGAL_PARAM_ERROR, null, null)
			.setMsg("DISK type cache must be implemented by customer");
		default: 
			cache = MEMCACHE.getOperator(expireTime, unit, byteSize);
			break;
		}
		LOCALCACHE.putIfAbsent(key, cache);
		return cache;
	}
	
	public static CacheOperator<String, Object> getLocalCache(String key) {
		return LOCALCACHE.getOrDefault(key, null);
	}
	
	public static void removeCache(String key) {
		LOCALCACHE.remove(key);
	}
}
