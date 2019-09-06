package com.mint.service.email;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.database.annotation.EnableJpaOps;
import com.mint.service.email.annotation.EmailConfig;
import com.mint.service.email.metadata.EmailServiceMetadataProvider;
import com.mint.service.email.route.RoutingStrategy;

@MintService(metadataProvider = EmailServiceMetadataProvider.class)
@EnableJpaOps
@EmailConfig(strategy = RoutingStrategy.HASH)
public class MintEmailServiceStarter {

	public static void main(String[] args) {
		SpringApplication.run(MintEmailServiceStarter.class, args);
	}
	
}
