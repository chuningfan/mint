package com.mint.service.interceptor.net;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.interceptor.MintInterceptor;


public class ContextInterceptor extends MintInterceptor {

	private final ContextWrapper wrapper;
	
	public ContextInterceptor() {
		ServiceContext.userContextPool = new ThreadLocal<>();
		wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		ServiceContext.userContextPool.remove();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserContext context = wrapper.getFromReq(request);
		if (context != null) {
			ServiceContext.userContextPool.set(context);
		}
		return true;
	}
	
}
