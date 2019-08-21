package com.mint.service.interceptor.net;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.context.UserContextThreadLocal;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.exception.Exceptions;
import com.mint.service.exception.MintServiceException;
import com.mint.service.interceptor.MintInterceptor;


public class ContextInterceptor extends MintInterceptor {

	private final ContextWrapper wrapper;
	
	public ContextInterceptor() {
		wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
		if (wrapper == null) {
			throw Exceptions.get(MintServiceException.class, "No implementation for ContextWrapper was found.");
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserContextThreadLocal.remove();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserContext context = wrapper.getFromReq(request);
		if (context != null) {
			UserContextThreadLocal.set(context);
		}
		return true;
	}
	
}
