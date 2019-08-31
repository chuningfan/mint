package com.mint.service.pipeline.pre;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import com.mint.common.context.TokenHandler;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.pipeline.ServicePipelineMember;

public class AuthValidator implements ServicePipelineMember<String> {

	public static final String KEY = "/service/";
	
	public static final String ID = "auth-validator";
	
	private final TokenHandler tokenHandler;
	
	public AuthValidator() {
		tokenHandler = CommonServiceLoader.getSingleService(TokenHandler.class, ServiceContext.beanFactory);
		if (tokenHandler == null) {
			throw MintException.getException(Error.IMPLEMENTATION_NOT_FOUND_ERROR, null, null);
		}
	}
	
	@Override
	public String id() {
		return ID;
	}


	@Override
	public void doValidate(HttpServletRequest req, HttpServletResponse resp, String t) throws MintException {
		String uri = req.getRequestURI();
		if (!uri.contains(KEY)) {
			return;
		}
		if (StringUtils.isEmpty(t) || !tokenHandler.validate(t)) {
			throw MintException.getException(Error.INVALID_CONTEXT_ERROR, null, null);
		}
	}
	
}
