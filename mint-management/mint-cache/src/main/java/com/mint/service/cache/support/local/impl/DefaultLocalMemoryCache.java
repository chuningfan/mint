package com.mint.service.cache.support.local.impl;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.assertj.core.util.Arrays;
import org.yaml.snakeyaml.util.ArrayUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.mint.service.cache.support.CacheOperator;
import com.mint.service.cache.support.local.LocalCache;
import com.mint.service.cache.support.local.LocalCacheType;

public class DefaultLocalMemoryCache implements LocalCache {

	@Override
	public LocalCacheType getType() {
		return LocalCacheType.MEMORY;
	}

	@Override
	public CacheOperator<String, Object> getOperator(Long expire, TimeUnit unit, Long byteSize) {
		return new DefaultMemCache(expire, unit, byteSize);
	}
	
	public static final class DefaultMemCache implements CacheOperator<String, Object> {

		private final Cache<Object, Object> cache;
		
		private DefaultMemCache(Long expire, TimeUnit unit, Long byteSize) {
			cache = CacheBuilder.newBuilder()
					.expireAfterWrite(expire, unit)
					.maximumSize(byteSize).build();
		}
		
		@Override
		public Object getByKey(String key) {
			return cache.getIfPresent(key);
		}

		@Override
		public Collection<Object> getByKeys(Collection<String> keys) {
			Collection<Object> coll = null;
			if (keys != null && !keys.isEmpty()) {
				coll = Lists.newArrayList();
				Object val = null;
				for (String k: keys) {
					val = getByKey(k);
					if (val == null) {
						continue;
					}
					coll.add(val);
				}
			}
			return coll;
		}

		@Override
		public void store(String key, Object value, Long expireTime, TimeUnit unit) {
			cache.put(key, value);
		}

		@Override
		public void removeIfPresent(String... keys) {
			if (!Arrays.isNullOrEmpty(keys)) {
				if (keys.length == 1) {
					cache.invalidate(keys);
				} else {
					List<String> keyList = ArrayUtils.toUnmodifiableList(keys);
					cache.invalidateAll(keyList);
				}
			}
		}

		@Override
		public boolean hasKey(String key) {
			return cache.asMap().containsKey(key);
		}

		@Override
		public void changeExpireTime(String key, Long expireTime, TimeUnit unit) {
		}
		
	}

}
