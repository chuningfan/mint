package com.mint.service.app;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.app.meta.AppServiceMetadataProvider;
import com.mint.service.mq.annotation.EnableMintMQ;

@MintService(metadataProvider = AppServiceMetadataProvider.class)
@EnableMintMQ
public class AppServiceStarter {
	
	public static void main(String[] args) {
		SpringApplication.run(AppServiceStarter.class, args);
	}
	
}
