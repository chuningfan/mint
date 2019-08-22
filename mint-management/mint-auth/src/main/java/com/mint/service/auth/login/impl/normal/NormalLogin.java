package com.mint.service.auth.login.impl.normal;

import org.springframework.stereotype.Component;

import com.mint.common.context.UserContext;
import com.mint.common.dto.login.LoginFormData;
import com.mint.service.auth.login.LoginHandler;

@Component
public class NormalLogin extends LoginHandler<LoginFormData>  {
	
	@Override
	protected UserContext login(LoginFormData formData) {
		
		return null;
	}
	
}
