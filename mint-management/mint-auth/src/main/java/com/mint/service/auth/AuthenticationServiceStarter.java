package com.mint.service.auth;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.auth.metadata.AuthServiceMetadataProvider;

@MintService(metadataProvider = AuthServiceMetadataProvider.class, 
contextInterceptorExcludePaths = {"/service/n_auth/doReg", "/service/n_auth/doLogin", "/service/n_auth/doTest/**"})
public class AuthenticationServiceStarter {
	
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceStarter.class, args);
	}
	
}
