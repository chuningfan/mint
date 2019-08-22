package com.mint.service.auth.logout.impl;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Component;

import com.mint.service.auth.listener.dto.NormalLogoutDto;
import com.mint.service.auth.logout.LogoutHandler;

@Component
public class NormalLogout extends LogoutHandler<NormalLogoutDto> {

	@Override
	protected void doLogout(NormalLogoutDto data) throws IOException, ServletException {
		data.getRequest().getRequestDispatcher("/login").forward(data.getRequest(), data.getResponse());
	}

}
