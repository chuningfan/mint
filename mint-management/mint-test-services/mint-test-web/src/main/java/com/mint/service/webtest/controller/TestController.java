package com.mint.service.webtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.service.webtest.mq.MQSender;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private MQSender mqSender;
	
	
	@GetMapping("/showName")
	public String showName() {
		return null;
	}
	
	@GetMapping("/send/{msg}")
	public String sendMsg(@PathVariable String msg) throws Exception {
		mqSender.send(msg, null);
		return "SENT";
	}
	
}
