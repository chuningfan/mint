package com.mint.service.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mint.common.context.UserContext;
import com.mint.service.exception.MintServiceException;
import com.mint.service.pipeline.ServicePipelineMember;

public class BlackListValidation implements ServicePipelineMember {

	public static final String ID = "mint-blacklist-validator";
	
	@Override
	public String id() {
		return ID;
	}

	@Override
	public void validate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintServiceException {
		
	}

}
