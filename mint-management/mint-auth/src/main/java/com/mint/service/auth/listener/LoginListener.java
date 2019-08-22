package com.mint.service.auth.listener;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.UserContext;
import com.mint.service.auth.listener.dto.NormalLoginDto;
import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.security.sault.Base64Util;

@Component
public class LoginListener implements Observer {

	private static final Logger LOG = LoggerFactory.getLogger(LoginListener.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Value("${auth.redis.expire.timeMs}")
	private String rExpireTimeMs;
	
	@Value("${auth.cookie.expire.timeMs}")
	private String cExpireTimeMs;
	
	@Value("${auth.cookie.domain}")
	private String cookieDomain;
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof NormalLoginDto) {
			NormalLoginDto dto = (NormalLoginDto) arg;
			UserContext context = dto.getContext();
			HttpServletResponse response = dto.getResponse();
			try {
				redisHelper.store(context.getUserId().toString(), context, Long.parseLong(rExpireTimeMs), TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				LOG.error("Storing context into REDIS error: {}", e.getMessage());
				LOG.info("Attempting to store context in cookie");
				try {
					String contextJson = mapper.writeValueAsString(context);
					String cookieValue = Base64Util.encode(contextJson);
					Cookie cookie = new Cookie(UserContextKeys.USER_CONTEXT, cookieValue);
					cookie.setPath("/");
					cookie.setDomain(cookieDomain);
					cookie.setMaxAge(-1);
					response.addCookie(cookie);
				} catch (NoSuchAlgorithmException | UnsupportedEncodingException | JsonProcessingException e1) {
				}
			}
			
		}
	}
	
}
