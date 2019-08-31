package com.mint.common.context;

import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWTVerifier;

public interface TokenHandler {

	boolean validate(String token);
	
	String create(UserContext context, long expireTime, TimeUnit unit);
	
	LiteUserContext parse(String token);
	
	JWTVerifier getVerifier();
	
}
