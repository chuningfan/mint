package com.mint.service.security.context;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.context.UserContextThreadLocal;
import com.mint.common.utils.HttpUtil;
import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class UserContextWrapperImpl implements ContextWrapper {
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public UserContext getFromReq(HttpServletRequest req) {
		String cookieValue = HttpUtil.getCookieValue(req, UserContextKeys.USER_CONTEXT);
		if (cookieValue == null) {
			cookieValue = req.getHeader(UserContextKeys.USER_TOKE);
			if (cookieValue == null) {
				return null;
			}
		}
		String userIdStr = ContextCookieUtil.getCookieRealValue(cookieValue);
		UserContext context = (UserContext) redisHelper.getByKey(userIdStr);
		if  (context == null) {
			return null;
		}
		autoDelayExpiration(userIdStr, context);
		return context;
	}

	@Override
	public HttpRequest setUserContextIntoRequestHeader(UserContext context, HttpRequest req) {
		req.getHeaders().add(UserContextKeys.USER_TOKE, context.getToken());
		return req;
	}

	private void autoDelayExpiration(String key, UserContext context) {
		long passedMs = System.currentTimeMillis() - context.getPrevLoginTime();
		long expirationPeriod = context.getExpirationPeriodMs();
		if (passedMs >= (expirationPeriod >>> 2)) {
			context.setPrevLoginTime(System.currentTimeMillis());
			redisHelper.store(key, context, expirationPeriod, TimeUnit.MILLISECONDS);
		}
	}
	
}
