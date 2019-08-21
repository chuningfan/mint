package com.mint.service.cache.support;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author ningfanchu
 *
 * @param <K>
 * @param <V>
 */
public interface CacheOperator<K, V> {
	
	V getByKey(K key);
	
	Collection<V> getByKeys(Collection<K> keys);
	
	void store(K key, V value, Long expireTime, TimeUnit unit);
	
	void removeIfPresent(@SuppressWarnings("unchecked") K...keys);
	
	boolean hasKey(K key);
	
	void changeExpireTime(String key, Long expireTime, TimeUnit unit);
	
}
