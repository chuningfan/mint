package com.mint.service.webtest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class WebTestController {

	@Value("${test.name}")
	private String name;
	
	@GetMapping("/test")
	public void test() {
		System.out.println("test");
	}
	
	@GetMapping("/showName")
	public String showName() {
		return name;
	}
	
}
