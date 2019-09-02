package com.mint.service.security.context;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class UserContextWrapperImpl implements ContextWrapper {

	@Autowired
	private RedisHelper redisHelper;

	@Override
	public void storeIntoCache(String key, UserContext context, long expireTime, TimeUnit unit) throws Exception {
		redisHelper.store(key, context, expireTime, unit);
	}

	@Override
	public void deleteFromCache(String...keys) throws Exception {
		redisHelper.removeIfPresent(keys);
	}

	@Override
	public boolean validate(String key) {
		return redisHelper.hasKey(key);
	}

}
