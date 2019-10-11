package com.mint.common.auth;

import java.util.concurrent.TimeUnit;

import com.mint.common.context.UserContext;

public interface AuthHandler {
	
	boolean validate(String cookieValue);
	
	String createCookieValue(UserContext context, long expireTime, TimeUnit unit);
	
}
