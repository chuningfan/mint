package com.mint.service.auth.exception;

import com.mint.common.enums.LoginResult;

public class AuthException extends Exception {

	private static final long serialVersionUID = -649039303309424340L;

	private LoginResult loginResult;
	
	public AuthException(LoginResult loginResult) {
		super("Auth failed.");
		this.loginResult = loginResult;
	}

	public LoginResult getLoginResult() {
		return loginResult;
	}
	
}
