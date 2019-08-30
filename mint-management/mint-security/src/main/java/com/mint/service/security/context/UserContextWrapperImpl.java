package com.mint.service.security.context;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.utils.HttpUtil;
import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class UserContextWrapperImpl implements ContextWrapper {
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public UserContext getFromReq(HttpServletRequest req) {
		String cookieValue = HttpUtil.getCookieValue(req, UserContextKeys.USER_CONTEXT);
		boolean fromInternal = false; // request comes from gateway
		if (cookieValue == null) {
			// if request comes from a internal server
			cookieValue = req.getHeader(UserContextKeys.USER_TOKE);
			if (cookieValue == null) {
				return null;
			}
			fromInternal = true;
		}
		String accountIdStr = ContextCookieUtil.getCookieRealValue(cookieValue);
		UserContext context = null;
		if (!fromInternal) {
			context = (UserContext) redisHelper.getByKey(accountIdStr);
			if (context != null) {
				autoDelayExpiration(accountIdStr, context);
			}
		} else {
			context = new UserContext();
			String roleIdStr = req.getHeader(UserContextKeys.USER_ROLE_IDS);
			if (StringUtils.hasText(roleIdStr)) {
				List<String> strList = Splitter.on(",").splitToList(roleIdStr);
				final Set<Long> roleIds = Sets.newHashSet();
				strList.stream().forEach(s -> {
					roleIds.add(Long.parseLong(s));
				});
				context.setRoleIds(roleIds);
			}
			context.setAccountId(Long.parseLong(accountIdStr));
		}
		return context;
	}

	@Override
	public HttpRequest setUserContextIntoRequestHeader(UserContext context, HttpRequest req) {
		req.getHeaders().add(UserContextKeys.USER_TOKE, context.getToken());
		req.getHeaders().add(UserContextKeys.USER_ROLE_IDS, CollectionUtils.isEmpty(context.getRoleIds()) ? null : Joiner.on(",").join(context.getRoleIds()));
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
