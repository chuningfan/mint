package com.mint.service.auth.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CookieTool {
	
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
