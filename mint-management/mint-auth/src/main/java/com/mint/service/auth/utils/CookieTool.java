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
	
	
	
	public void newCookie(HttpServletResponse resp, String key, String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Cookie c = new Cookie(key, ContextCookieUtil.createCookieValue(value));
		c.setDomain(domain);
		c.setPath("/");
		c.setMaxAge(-1);
		resp.addCookie(c);
	}
	
}
