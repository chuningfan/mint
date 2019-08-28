package com.mint.service.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.common.dto.web.WebResponse;
import com.mint.common.exception.MintException;
import com.mint.service.auth.core.ext.normal.NormalAuthHandler;
import com.mint.service.auth.enums.Action;
import com.mint.service.user.dto.login.LoginFormData;
import com.mint.service.user.dto.reg.CredentialFormData;

@RequestMapping("/auth")
@RestController
public class AuthController {
	
	@Autowired
	private NormalAuthHandler normalAuthHandler;
	
	
	@PostMapping(value = "/doReg", 
			consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, 
			produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public WebResponse<Boolean> doReg(@RequestBody CredentialFormData formData, HttpServletRequest req, HttpServletResponse resp) throws MintException {
		return normalAuthHandler.route(Action.DO_REG, formData.getUsername(), formData.getPassword());
	}
	
	@PostMapping(value = "/doLogin", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE}, 
			produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
	public WebResponse<Boolean> doLogin(@RequestBody LoginFormData formData, HttpServletRequest req, HttpServletResponse resp) throws MintException {
		return normalAuthHandler.route(Action.DO_LOGIN, req, resp, formData.getUsername(), formData.getPassword());
	}
	
}
