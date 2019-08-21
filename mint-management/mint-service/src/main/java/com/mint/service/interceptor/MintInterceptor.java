package com.mint.service.interceptor;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public abstract class MintInterceptor implements HandlerInterceptor {
	
	protected DefaultListableBeanFactory beanFactory;
	
	protected MintInterceptor() {
	}
	
}
