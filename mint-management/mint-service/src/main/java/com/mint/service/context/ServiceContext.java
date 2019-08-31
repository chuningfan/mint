package com.mint.service.context;

import java.util.List;

import org.springframework.beans.factory.ListableBeanFactory;

import com.mint.common.exception.advice.ExceptionDataProcessor;
import com.mint.service.metadata.ServiceMetadataProvider;

public class ServiceContext {

	public static ListableBeanFactory beanFactory;

	public volatile static String id;

	public volatile static List<Long> supportedRoleIds;

	public volatile static String serverAddress;

	public volatile static int readTimeout = 30000;

	public volatile static int connectTimeout = 10000;

	public volatile static int longConnectionReadTimeout = 180000;

	public volatile static boolean useServiceRequestInterceptor = true;

	public volatile static boolean useServiceDefaultLogInterceptor = true;

	public static Class<? extends ServiceMetadataProvider> metadataProvider = null;
	
	public static Class<? extends ExceptionDataProcessor> exceptionDataProcessor = null;
	
}
