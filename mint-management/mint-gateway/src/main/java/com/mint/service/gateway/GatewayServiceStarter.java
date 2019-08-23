package com.mint.service.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.mint.service.annotation.MintService;
import com.mint.service.gateway.metadata.GatewayServiceMetadataProvider;
import com.mint.service.interceptor.net.ContextInterceptor;

@MintService(metadataProvider = GatewayServiceMetadataProvider.class, 
noAutoConfigFor = DataSourceAutoConfiguration.class, excludeInterceptors = ContextInterceptor.class)
@EnableZuulProxy
public class GatewayServiceStarter {
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceStarter.class, args);
	}
	
}
