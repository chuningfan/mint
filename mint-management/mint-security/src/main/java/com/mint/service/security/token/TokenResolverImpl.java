package com.mint.service.security.token;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.LiteUserContext;
import com.mint.common.context.TokenHandler;
import com.mint.common.context.TokenResolver;
import com.mint.common.context.UserContext;
import com.mint.common.utils.HttpUtil;
import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class TokenResolverImpl implements TokenResolver {

	@Autowired
	private TokenHandler tokenHandler;
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public void setTokenToRequestHeader(String token, HttpRequest req) {
		req.getHeaders().add(UserContextKeys.USER_CONTEXT, token);
	}

	@Override
	public void autoRefreshExpiration(String token, HttpServletRequest req, HttpServletResponse resp) {
		if (!StringUtils.isEmpty(token) && tokenHandler.validate(token)) {
			LiteUserContext context = tokenHandler.parse(token);
			long passedMs = System.currentTimeMillis() - context.getPrevLoginTime();
			long expirationPeriod = context.getExpirePeriodMs();
			if (passedMs >= (expirationPeriod >>> 1)) {
				context.setPrevLoginTime(System.currentTimeMillis());
				HttpUtil.deleteCookiesByKey(req, resp, UserContextKeys.USER_CONTEXT);
				newCookie(resp, UserContextKeys.USER_CONTEXT, tokenHandler.create(UserContext.fromLite(context), expirationPeriod, TimeUnit.MILLISECONDS), context.getCookieDomain());
				redisHelper.store(context.getAccountId().toString(), context, expirationPeriod, TimeUnit.MILLISECONDS);
			}
		}
	}
	
	public static String newCookie(HttpServletResponse resp, String key, String token, String domain) {
		Cookie c = new Cookie(key, token);
		c.setDomain(domain);
		c.setHttpOnly(true);
		c.setPath("/");
		c.setMaxAge(3600 * 24 * 7);
		resp.addCookie(c);
		return token;
	}

}
