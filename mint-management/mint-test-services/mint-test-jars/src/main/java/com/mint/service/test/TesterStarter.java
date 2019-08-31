package com.mint.service.test;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.database.annotation.EnableJpaOps;
import com.mint.service.test.meta.TesterServiceMetadataProvider;

@MintService(metadataProvider = TesterServiceMetadataProvider.class)
@EnableJpaOps(basePackages="com.mint.service.test.db.jpa")
public class TesterStarter {

	public static void main(String[] args) {
		SpringApplication.run(TesterStarter.class, args);
	}
	
}
