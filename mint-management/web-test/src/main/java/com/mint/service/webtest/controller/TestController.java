package com.mint.service.webtest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@Value("${test.name}")
	private String name;
	
	@GetMapping("/showName")
	public String showName() {
		return name;
	}
	
}
