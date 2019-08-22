package com.mint.service.cache.support.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mint.service.cache.support.CacheOperator;

/**
 * 
 * @author ningfanchu
 *
 */
@Component
@ConditionalOnBean(RedisTemplateConfiguration.class)
public class RedisHelper implements CacheOperator<String, Object> {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public RedisTemplate<String, Object> getRedisTemplate() {
		return this.redisTemplate;
	}
	
	@Override
	public Object getByKey(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	@Override
	public Collection<Object> getByKeys(Collection<String> keys) {
		return CollectionUtils.isEmpty(keys) ? null : redisTemplate.opsForValue().multiGet(keys);
	}

	@Override
	public void store(String key, Object value, Long expireTime, TimeUnit unit) {
		if (expireTime != null && expireTime > 0 && unit != null) {
			redisTemplate.opsForValue().set(key, value, expireTime, unit);
		} else {
			redisTemplate.opsForValue().set(key, value);
		}
	}

	@Override
	public void removeIfPresent(String... keys) {
		if (keys == null || keys.length == 0) {
			return;
		}
		List<String> keyList = Stream.of(keys).collect(Collectors.toList());
		redisTemplate.opsForValue().getOperations().delete(keyList);
	}

	@Override
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public void changeExpireTime(String key, Long expireTime, TimeUnit unit) {
		redisTemplate.expire(key, expireTime, unit);
	}

	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	// ================================Map=================================
	public Object hget(String key, Object item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	public boolean hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean hmset(String key, Map<String, Object> map, long expireTime, TimeUnit unit) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (expireTime > 0) {
				changeExpireTime(key, expireTime, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean hset(String key, Object item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean hset(String key, Object item, Object value, long expireTime, TimeUnit unit) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (expireTime > 0) {
				changeExpireTime(key, expireTime, unit);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}

	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	public double hincr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	public double hdecr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}

	// ============================set=============================
	public Set<Object> sGet(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public long sSetAndTime(String key, long expireTime, TimeUnit unit, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (expireTime > 0)
				changeExpireTime(key, expireTime, unit);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public long setRemove(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	// ===============================list=================================
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean lSet(String key, Object value, long expireTime, TimeUnit unit) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (expireTime > 0)
				changeExpireTime(key, expireTime, unit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean lSet(String key, List<Object> value, long expireTime, TimeUnit unit) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (expireTime > 0)
				changeExpireTime(key, expireTime, unit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public long lRemove(String key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// ===============================lock=================================
	private static final Long OK = 1L;
	public boolean globalLock(String key, String val, Long expireTime, TimeUnit unit) {
		boolean flag = false;
        try{
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";
            RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(key),val,unit.toMillis(expireTime));
            if(OK.equals(result)){
                return true;
            }
        }catch(Exception e){
        	return flag;
        }
        return flag;
	}

	public boolean releaseLock(String lockKey, String value){
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),value);
        if(OK.equals(result)) {
            return true;
        }
        return false;
    }
}
