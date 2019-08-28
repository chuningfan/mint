package com.mint.service.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mint.common.context.UserContextThreadLocal;
import com.mint.service.auth.exception.AuthException;

@RequestMapping("/page")
@Controller
public class PageController {

	@GetMapping("/login")
	public String login() throws AuthException {
		if (hasLogged()) {
			return "/index";
		}
		return "/login";
	}
	
	@GetMapping("/index")
	public String index() throws AuthException {
		return "/index";
	}
	
	@GetMapping("/register")
	public String register() throws AuthException {
		if (hasLogged()) {
			return "/index";
		}
		return "/register";
	}

	private boolean hasLogged() {
		return UserContextThreadLocal.get() != null;
	}
	
}
