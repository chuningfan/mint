package com.mint.service.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mint.service.pipeline.PipelineProvider;

public class InterceptorConfiguration implements WebMvcConfigurer {

	private PipelineProvider pipelineProvider;
	
	public InterceptorConfiguration(PipelineProvider pipelineProvider) {
		this.pipelineProvider = pipelineProvider;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		ServiceInterceptor interceptor = new ServiceInterceptor(pipelineProvider);
		registry.addInterceptor(interceptor).addPathPatterns("/service/**")
		.excludePathPatterns("/resources/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
