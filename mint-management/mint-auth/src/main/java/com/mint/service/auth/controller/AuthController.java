package com.mint.service.auth.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.dto.login.LoginFormData;
import com.mint.common.dto.reg.NormalRegDto;
import com.mint.common.utils.HttpUtil;
import com.mint.service.auth.exception.AuthException;
import com.mint.service.auth.listener.dto.NormalLogoutDto;
import com.mint.service.auth.login.impl.normal.NormalLogin;
import com.mint.service.auth.logout.impl.NormalLogout;
import com.mint.service.auth.reg.RegHandler;
import com.mint.service.security.context.ContextCookieUtil;

@RestController
@RequestMapping("/service")
public class AuthController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private NormalLogin normalLogin;
	
	@Autowired
	private RegHandler regHandler;
	
	@Autowired
	private NormalLogout normalLogout;
	
	@Value("${auth.page.login}")
	private String loginPage;
	
	@GetMapping("/nLogin")
	public String nLogin() {
		return "NormalLogin";
	}
	
	@PostMapping("/doNLogin")
	public void doNLogin(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginFormData formData) throws IOException, ServletException, AuthException {
		try {
			normalLogin.doLogin(formData);
			response.sendRedirect(formData.getTargetUrl());
		} catch (AuthException e) {
			throw e;
		}
	}
	
	@GetMapping("/nReg")
	public String nReg() {
		return "NormalReg";
	}
	
	@PostMapping("/doNReg")
	public void doNReg(@RequestBody NormalRegDto normalRegDto) throws IOException, ServletException {
		regHandler.doNReg(normalRegDto);
	}
	
	@PostMapping("/doNLogout")
	public String doNLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NoSuchAlgorithmException {
		String cookieValie = HttpUtil.getCookieValue(request, UserContextKeys.USER_CONTEXT);
		if (!StringUtils.isEmpty(cookieValie)) {
			String userIdStr = ContextCookieUtil.getCookieRealValue(cookieValie);
			NormalLogoutDto dto = new NormalLogoutDto();
			dto.setRequest(request);
			dto.setResponse(response);
			dto.setUserIdStr(userIdStr);
			normalLogout.doLogout(dto);
		}
		return "NormalLogin";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword() throws IOException, ServletException, NoSuchAlgorithmException {
		return "NormalLogin";
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
