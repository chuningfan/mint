package com.mint.service.security.auth;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.common.auth.AuthHandler;
import com.mint.common.context.UserContext;
import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class RedisAuthhandlerImpl implements AuthHandler {

	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public boolean validate(String cookieValue) {
		return redisHelper.getByKey(cookieValue) != null;
	}

	@Override
	public String createCookieValue(UserContext context, long expireTime, TimeUnit unit) {
		String cookieValue = null;
		redisHelper.store(cookieValue = context.getAccountId().toString(), context, expireTime, unit);
		return cookieValue;
	}

}
