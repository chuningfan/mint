package com.mint.service.pipeline.pre;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.pipeline.ServicePipelineMember;

public class AuthValidator implements ServicePipelineMember {

	public static final String KEY = "/service/";
	
	public static final String ID = "auth-validator";
	
	private final ContextWrapper wrapper;
	
	public AuthValidator() {
		wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
		if (wrapper == null) {
			throw MintException.getException(Error.IMPLEMENTATION_NOT_FOUND_ERROR, null, null);
		}
	}
	
	@Override
	public String id() {
		return ID;
	}


	@Override
	public void doValidate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintException {
		String uri = req.getRequestURI();
		if (!uri.contains(KEY)) {
			return;
		}
		if (context == null) {
			throw MintException.getException(Error.INVALID_CONTEXT_ERROR, null, null);
		}
		roleValidation(req, resp, context);
	}

	private void roleValidation(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintException {
		if (CollectionUtils.isEmpty(ServiceContext.supportedRoleIds)) {
			return; // if service has no role limitations
		}
		Set<Long> supportedRoleIds = context.getRoleIds();
		if (CollectionUtils.isEmpty(supportedRoleIds)) {
			throw MintException.getException(Error.ROLE_DISALLOWED_ERROR, null, null);
		}
		if (!ServiceContext.supportedRoleIds.stream().anyMatch(i -> supportedRoleIds.contains(i))) {
			MintException exc = MintException.getException(Error.ROLE_DISALLOWED_ERROR, null, null);
			exc.setMsg(String.format("User request is illegal. Service %s just supports role id %s", 
					ServiceContext.id, Joiner.on(",").join(ServiceContext.supportedRoleIds)));
			throw exc;
		}
	}
	
}
