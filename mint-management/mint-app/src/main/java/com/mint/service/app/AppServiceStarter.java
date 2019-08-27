package com.mint.service.app;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.app.meta.AppServiceMetadataProvider;

@MintService(metadataProvider = AppServiceMetadataProvider.class)
public class AppServiceStarter {
	
	public static void main(String[] args) {
		SpringApplication.run(AppServiceStarter.class, args);
	}
	
}
