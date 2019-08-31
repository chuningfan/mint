package com.mint.common.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;

/**
 * 
 * @author ningfanchu
 *
 */
public interface TokenResolver  {
	
	void setTokenToRequestHeader(String token, HttpRequest req);
	
	void autoRefreshExpiration(String token, HttpServletRequest req, HttpServletResponse resp);
	
}
