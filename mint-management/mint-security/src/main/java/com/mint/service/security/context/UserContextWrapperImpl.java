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
	public void storeIntoCache(String key, UserContext context) throws Exception {
		redisHelper.store(key, context, 3600 * 1000 * 24L, TimeUnit.MILLISECONDS);
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
