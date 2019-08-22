package com.mint.service.security.guard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.common.context.UserContext;
import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.security.exception.ViciousRequestException;

@Component
public class BlackListConcierge {

	@Autowired
	private RedisHelper redisHelper;
	
	public void validate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws ViciousRequestException, Exception {
		// TODO according to REDIS to filter black request.
		
	}
	
}
