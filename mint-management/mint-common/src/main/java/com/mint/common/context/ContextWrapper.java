package com.mint.common.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;

/**
 * SPI 接口，外部实现context 获取方法。
 * 
 * @author ningfanchu
 *
 */
public interface ContextWrapper {
	
	UserContext getFromReq(HttpServletRequest req) throws Exception;
	
	HttpRequest setUserContextIntoRequestHeader(UserContext context, HttpRequest req);
	
}
