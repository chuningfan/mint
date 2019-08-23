package com.mint.service.webtest;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.interceptor.net.ContextInterceptor;
import com.mint.service.webtest.metadata.WebTestServiceMetadataProvider;

@MintService(metadataProvider = WebTestServiceMetadataProvider.class, excludeInterceptors = ContextInterceptor.class)
public class Starter {
	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}
}
