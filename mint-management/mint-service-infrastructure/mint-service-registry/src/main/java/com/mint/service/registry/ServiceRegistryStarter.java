package com.mint.service.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistryStarter {
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistryStarter.class, args);
	}
	
}
