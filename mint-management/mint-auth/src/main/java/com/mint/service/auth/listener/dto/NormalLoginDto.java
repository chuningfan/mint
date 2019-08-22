package com.mint.service.auth.listener.dto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mint.common.context.UserContext;

public class NormalLoginDto {
	
	private UserContext context;
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;

	public UserContext getContext() {
		return context;
	}

	public void setContext(UserContext context) {
		this.context = context;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
}
