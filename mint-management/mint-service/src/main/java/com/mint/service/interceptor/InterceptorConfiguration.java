package com.mint.service.interceptor;

import java.lang.reflect.Field;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mint.service.context.ServiceContext;
import com.mint.service.interceptor.net.ContextInterceptor;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (ServiceContext.interceptors != null && ServiceContext.interceptors.length > 0) {
			MintInterceptor interceptor = null;
			Field f = null;
			registry.addInterceptor(new ContextInterceptor()).addPathPatterns("/service/*")
			.excludePathPatterns("/resources/*");
			try {
				for (Class<? extends MintInterceptor> hi : ServiceContext.interceptors) {
					interceptor = hi.newInstance();
					f = interceptor.getClass().getSuperclass().getDeclaredField("beanFactory");
					f.setAccessible(true);
					f.set(interceptor, ServiceContext.beanFactory);
					registry.addInterceptor(interceptor).addPathPatterns("/service/*")
							.excludePathPatterns("/resources/*");
				}
			} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			}

		}
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
