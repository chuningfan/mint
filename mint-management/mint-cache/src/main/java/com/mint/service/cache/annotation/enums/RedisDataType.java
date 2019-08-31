package com.mint.service.cache.annotation.enums;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.mint.service.cache.support.redis.RedisHelper;

@SuppressWarnings("unused")
public enum RedisDataType {
	
	STRING {
		public void save(RedisHelper helper, String key, Long expireTime, TimeUnit unit, Object...data) {
			helper.store(key, data, expireTime, unit);
		}

		public Object find(RedisHelper helper, String key) {
			return helper.getByKey(key);
		}
		
		public void delete(RedisHelper helper, String key) {
			helper.removeIfPresent(key);
		}
	}, SET {
		public void save(RedisHelper helper, String key, Long expireTime, TimeUnit unit, Object...data) {
			helper.sSet(key, data);
		}

		public Set<Object> find(RedisHelper helper, String key) {
			return helper.sGet(key);
		}
		
		public void delete(RedisHelper helper, String key) {
			helper.setRemove(key, helper.sGet(key));
		}
		
		public void delete(RedisHelper helper, String key, Object...values) {
			helper.setRemove(key, values);
		}
		
	}, HASH {
		public void save(RedisHelper helper, String key, Long expireTime, TimeUnit unit, Object...data) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> map = (Map<Object, Object>) data[0];
			helper.hmset(key, map);
		}
		
		public void save(RedisHelper helper, String key, Object mapKey, Object mapValue) {
			helper.hset(key, mapKey, mapValue);
		}

		public Map<Object, Object> find(RedisHelper helper, String key) {
			return helper.hmget(key);
		}
		
		public Object find(RedisHelper helper, String key, Object mapKey) {
			return helper.hget(key, mapKey);
		}
		
		public void delete(RedisHelper helper, String key) {
		}
		
		public void delete(RedisHelper helper, String key, Object mapKey) {
			helper.hdel(key, mapKey);
		}
		
	}, LIST {
		public void save(RedisHelper helper, String key, Long expireTime, TimeUnit unit, Object...data) {
			helper.lSet(key, data, expireTime, unit);
		}

		public List<Object> find(RedisHelper helper, String key) {
			long size = helper.lGetListSize(key);
			return helper.lGet(key, 0, size - 1);
		}
		
		public void delete(RedisHelper helper, String key, long index, Object...data) {
			for (Object d: data) {
				helper.lRemove(key, index, d);
			}
		}
	};
	
}
