package com.mint.service.email;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.database.annotation.EnableJpaOps;
import com.mint.service.email.metadata.EmailServiceMetadataProvider;

@MintService(metadataProvider = EmailServiceMetadataProvider.class)
@EnableJpaOps
public class MintEmailServiceStarter {

	public static void main(String[] args) {
		SpringApplication.run(MintEmailServiceStarter.class, args);
	}
	
}
