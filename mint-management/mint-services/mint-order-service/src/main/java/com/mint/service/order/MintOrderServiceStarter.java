package com.mint.service.order;

import org.springframework.boot.SpringApplication;

import com.mint.service.annotation.MintService;
import com.mint.service.order.metadata.OrderServiceMetadataProvider;

@MintService(metadataProvider = OrderServiceMetadataProvider.class)
public class MintOrderServiceStarter {

	public static void main(String[] args) {
		SpringApplication.run(MintOrderServiceStarter.class, args);
	}
	
}
