package com.mint.security.context;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;

import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;

public class UserContextWrapperImpl implements ContextWrapper {

	@Override
	public UserContext getFromReq(HttpServletRequest req) throws Exception {
		UserContext context = new UserContext();
		context.setUserId(1L);
		context.setToken("123");
		return context;
	}

	@Override
	public HttpRequest setUserContextIntoRequestHeader(UserContext context, HttpRequest req) {
		req.getHeaders().add("usercontext", context.getToken());
		return req;
	}

}
