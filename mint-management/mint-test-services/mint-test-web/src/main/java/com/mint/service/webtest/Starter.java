package com.mint.service.webtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MintService(metadataProvider = WebTestServiceMetadataProvider.class)
@SpringBootApplication
public class Starter {
	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}
}
