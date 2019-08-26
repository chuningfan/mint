package com.mint.service.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mint.service.auth.exception.AuthException;

@RequestMapping("/page")
@Controller
public class PageController {

	@GetMapping("/reg")
	public String reg() throws AuthException {
		return "regPage";
	}
	
	@GetMapping("/login")
	public String login() throws AuthException {
		return "/login";
	}
	
}
