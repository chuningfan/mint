package com.mint.service.auth.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.service.auth.core.ext.normal.NormalAuthHandler;
import com.mint.service.auth.enums.Action;
import com.mint.service.auth.exception.AuthException;
import com.mint.service.user.dto.login.LoginFormData;
import com.mint.service.user.dto.reg.CredentialFormData;

@RequestMapping("/auth")
@RestController
public class AuthController {
	
	@Autowired
	private NormalAuthHandler normalAuthHandler;
	
	@Autowired
	private PageController pageController;
	
	@PostMapping(value = "/doReg", 
			consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, 
			produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public void doReg(@RequestBody CredentialFormData formData, HttpServletRequest req, HttpServletResponse resp) throws AuthException, ServletException, IOException {
		boolean flag = normalAuthHandler.route(Action.DO_REG, formData.getUsername(), formData.getPassword());
		if (flag) {
			pageController.login();
		}
	}
	
	@PostMapping(value = "/doLogin", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, 
			produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public boolean doLogin(@RequestBody LoginFormData formData, HttpServletResponse resp) throws AuthException {
		return normalAuthHandler.route(Action.DO_LOGIN, resp, formData.getUsername(), formData.getPassword());
	}
	
	@GetMapping("/reg")
	public String reg() throws AuthException {
		return "regPage";
	}
	
	@GetMapping("/login")
	public String login() throws AuthException {
		return "/login";
	}
	
}
