package com.mint.service.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.service.test.service.HibernateTesterService;
import com.mint.service.test.service.JPATestService;

@RestController
@RequestMapping("/service")
public class TestController {

	@Autowired
	private HibernateTesterService hibernateTesterService;
	
	@Autowired
	private JPATestService jpaTestService;
	
	@GetMapping("/hs")
	public void hibernateSave() {
		hibernateTesterService.save();
	}
	
	@GetMapping("/js")
	public void jpaSave() {
		jpaTestService.save();
	}
	
	@GetMapping("/hu")
	public void hibernateUpdate() {
		hibernateTesterService.update();
	}
	
	@GetMapping("/ju")
	public void jpaUpdate() {
		jpaTestService.update();
	}
	
}
