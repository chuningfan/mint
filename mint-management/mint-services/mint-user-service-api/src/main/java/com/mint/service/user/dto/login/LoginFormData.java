package com.mint.service.user.dto.login;

import com.mint.common.enums.LoginType;

public class LoginFormData {
	
	private String username;
	
	private String password;

	private LoginType loginType;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}
	
}
