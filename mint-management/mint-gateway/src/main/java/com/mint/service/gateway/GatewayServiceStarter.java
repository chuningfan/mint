package com.mint.service.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import com.mint.service.annotation.MintService;
import com.mint.service.gateway.metadata.GatewayServiceMetadataProvider;

@MintService(metadataProvider = GatewayServiceMetadataProvider.class, 
noAutoConfigFor = DataSourceAutoConfiguration.class)
@EnableZuulProxy
public class GatewayServiceStarter {
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceStarter.class, args);
	}
	
}
