package com.mint.service.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.service.auth.core.ext.normal.NormalAuthHandler;
import com.mint.service.auth.enums.Action;

@RequestMapping("/auth")
@RestController
public class AuthController {

	@Autowired
	private NormalAuthHandler normalAuthHandler;
	
}
