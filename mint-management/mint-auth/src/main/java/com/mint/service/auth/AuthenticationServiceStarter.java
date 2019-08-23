package com.mint.service.auth;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.auth.metadata.AuthServiceMetadataProvider;

@MintService(metadataProvider = AuthServiceMetadataProvider.class)
public class AuthenticationServiceStarter {
	
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceStarter.class, args);
	}
	
}
