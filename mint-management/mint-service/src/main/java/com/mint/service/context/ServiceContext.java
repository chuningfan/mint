package com.mint.service.context;

import java.util.List;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.mint.common.context.UserContext;
import com.mint.service.interceptor.MintInterceptor;

public class ServiceContext {
	
	public static ThreadLocal<UserContext> userContextPool;
	
	public static DefaultListableBeanFactory beanFactory;
	
	public volatile static String id;
	
	public volatile static List<Long> supportedRoleIds;
	
	public volatile static String serverAddress;
	
	public volatile static int readTimeout = 30000;
	
	public volatile static int connectTimeout = 10000;
	
	public volatile static int longConnectionReadTimeout = 180000;
	
	public volatile static boolean validateContext;
	
	public volatile static boolean useServiceRequestInterceptor = true;
	
	public volatile static boolean useServiceDefaultLogInterceptor = true;
	
	public volatile static Class<? extends MintInterceptor>[] interceptors = null;
	
}
