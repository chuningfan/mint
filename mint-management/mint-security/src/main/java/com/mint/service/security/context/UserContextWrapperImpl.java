package com.mint.service.security.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class UserContextWrapperImpl implements ContextWrapper {
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public UserContext getFromReq(HttpServletRequest req) throws Exception {
		UserContext context = new UserContext();
		req.getCookies();
		return context;
	}

	@Override
	public HttpRequest setUserContextIntoRequestHeader(UserContext context, HttpRequest req) {
		req.getHeaders().add(UserContextKeys.USER_TOKE, context.getToken());
		return req;
	}

}
