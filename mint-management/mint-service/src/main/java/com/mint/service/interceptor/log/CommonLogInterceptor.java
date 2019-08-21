package com.mint.service.interceptor.log;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import com.mint.service.interceptor.MintInterceptor;

public class CommonLogInterceptor extends MintInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(CommonLogInterceptor.class);
	
	private static final ThreadLocal<Long> TIMER = new ThreadLocal<>();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			String method = request.getMethod();
			Method m = hm.getMethod();
			LOG.info("{}: Invoking {}#{}", method, hm.getBean().getClass(), m.getName());
			TIMER.set(System.currentTimeMillis());
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			Method m = hm.getMethod();
			LOG.info("Invoked {}#{}. cost:{} ms", hm.getBean().getClass(), m.getName(), System.currentTimeMillis() - TIMER.get());
			if (ex != null) {
				LOG.error("Error encountered: {}", ex.getMessage());
			}
			TIMER.remove();
		}
	}

}
