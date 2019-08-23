package com.mint.service.auth.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.common.dto.login.LoginFormData;
import com.mint.service.auth.exception.AuthException;
import com.mint.service.auth.login.impl.normal.NormalLogin;

@RestController
@RequestMapping("/service")
public class AuthController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private NormalLogin normalLogin;
	
	@Value("${auth.page.login}")
	private String loginPage;
	
	@PostMapping("/nlogin")
	public void nLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginFormData formData) throws IOException, ServletException {
		try {
			normalLogin.doLogin(formData);
			response.sendRedirect(formData.getTargetUrl());
		} catch (AuthException e) {
			LOG.error(e.getMessage());
			request.getRequestDispatcher(loginPage).forward(request, response);
		}
	}
	
//	@PostMapping("/wlogin")
//	public String wLogin() {
//		
//	}
//	
//	@PostMapping("/plogin")
//	public String pLogin() {
//		
//	}
	
}
