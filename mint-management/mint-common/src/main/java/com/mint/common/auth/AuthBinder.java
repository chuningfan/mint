package com.mint.common.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;

public interface AuthBinder {

	void setCookieValueToRequestHeader(String cookieValue, HttpRequest req);
	
	void autoRefreshExpiration(String cookieValue, HttpServletRequest req, HttpServletResponse resp);
	
}
