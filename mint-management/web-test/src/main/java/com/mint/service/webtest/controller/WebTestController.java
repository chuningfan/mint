package com.mint.service.webtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class WebTestController {

	
	@GetMapping("/test")
	public void test() {
		System.out.println("test");
	}
	
}
