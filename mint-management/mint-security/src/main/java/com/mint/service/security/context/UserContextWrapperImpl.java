package com.mint.service.security.context;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.utils.HttpUtil;
import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class UserContextWrapperImpl implements ContextWrapper {
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public UserContext getFromReq(HttpServletRequest req) throws Exception {
		String contextStr = req.getHeader(UserContextKeys.USER_CONTEXT);
		UserContext context = null;
		if (!StringUtils.isEmpty(contextStr)) {
			context = objectMapper.readValue(contextStr.getBytes(), UserContext.class);
			return context;
		}
		String cookieValue = HttpUtil.getCookieValue(req, UserContextKeys.USER_CONTEXT);
		if (!StringUtils.isEmpty(cookieValue)) {
			String accountIdStr = ContextCookieUtil.getCookieRealValue(cookieValue);
			context = (UserContext) redisHelper.getByKey(accountIdStr);
			if (context != null) {
				autoDelayExpiration(accountIdStr, context);
			}
		}
		return context;
	}

	@Override
	public void setUserContextIntoRequestHeader(UserContext context, HttpRequest req) {
		try {
			req.getHeaders().add(UserContextKeys.USER_CONTEXT, objectMapper.writeValueAsString(context));
		} catch (JsonProcessingException e) {
			
		}
//		req.getHeaders().add(UserContextKeys.USER_ROLE_IDS, CollectionUtils.isEmpty(context.getRoleIds()) ? null : Joiner.on(",").join(context.getRoleIds()));
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
