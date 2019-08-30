package com.mint.service.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.common.dto.web.WebResponse;
import com.mint.service.rpc.RpcHandler;
import com.mint.service.user.dto.user.UserInfo;
import com.mint.service.user.service.UserService;

@RestController
@RequestMapping("/service")
public class ServiceController {
	
	@Autowired
	private RpcHandler rpcHandler;
	
	@GetMapping("/getUser/{id}")
	public WebResponse<UserInfo> getUser(@PathVariable("id") Long id) {
		UserService us = rpcHandler.get(UserService.class);
		return us.getUser(id);
	}
	
}
