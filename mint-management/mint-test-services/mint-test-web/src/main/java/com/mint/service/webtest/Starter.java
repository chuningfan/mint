package com.mint.service.webtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mint.service.mq.annotation.EnableMintMQ;

//@MintService(metadataProvider = WebTestServiceMetadataProvider.class)
@SpringBootApplication
@EnableMintMQ
public class Starter {
	public static void main(String[] args) {
		SpringApplication.run(Starter.class, args);
	}
}
