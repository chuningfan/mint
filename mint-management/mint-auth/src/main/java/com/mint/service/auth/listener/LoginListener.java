package com.mint.service.auth.listener;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.UserContext;
import com.mint.service.auth.listener.dto.NormalLoginDto;
import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.security.context.ContextCookieUtil;
import com.mint.service.security.sault.Base64Util;

@Component
public class LoginListener implements Observer {

	private static final Logger LOG = LoggerFactory.getLogger(LoginListener.class);
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Value("${auth.redis.expire.timeMs}")
	private String rExpireTimeMs;
	
	@Value("${auth.cookie.domain}")
	private String cookieDomain;
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof NormalLoginDto) {
			NormalLoginDto dto = (NormalLoginDto) arg;
			UserContext context = dto.getContext();
			HttpServletResponse response = dto.getResponse();
			try {
				String userIdStr = context.getUserId().toString();
				String cookieValue = ContextCookieUtil.createCookieValue(userIdStr);
				context.setToken(cookieValue);
				Cookie cookie = new Cookie(UserContextKeys.USER_CONTEXT, Base64Util.encode(cookieValue));
				cookie.setPath("/");
				cookie.setDomain(cookieDomain);
				cookie.setMaxAge(-1);
				response.addCookie(cookie);
				redisHelper.store(userIdStr, context, Long.parseLong(rExpireTimeMs), TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				LOG.error("Storing context into REDIS error: {}", e.getMessage());
			}
		}
	}
	
}
