package com.mint.service.auth.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mint.service.security.context.ContextCookieUtil;

@Component
public class CookieTool {
	
	@Value("${auth.cookie.domain}")
	private String domain;
	
	
	
	public String newCookie(HttpServletResponse resp, String key, String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String token = ContextCookieUtil.createCookieValue(value);
		Cookie c = new Cookie(key, token);
		c.setDomain(domain);
		c.setPath("/");
		c.setMaxAge(3600 * 24 * 365);
		resp.addCookie(c);
		return token;
	}
	
}
