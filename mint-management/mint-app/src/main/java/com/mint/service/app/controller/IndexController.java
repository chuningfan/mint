package com.mint.service.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mint.service.rpc.RpcHandler;
import com.mint.service.user.dto.user.UserInfo;
import com.mint.service.user.service.UserService;

@RequestMapping("/test")
@RestController
public class IndexController {
@Autowired
private RpcHandler handler;

	@GetMapping
	public String index()   {
		return "/index";
	}
	
	@GetMapping("/service/test")
	public @ResponseBody UserInfo test()   {
		UserService userService = handler.get(UserService.class);
		@SuppressWarnings("unused")
		UserInfo info  = userService.getUser(1L);
		System.out.println(123);
		return info;
	}
}
