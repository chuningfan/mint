package com.mint.service.pipeline.pre;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.exception.Exceptions;
import com.mint.service.exception.MintServiceException;
import com.mint.service.pipeline.ServicePipelineMember;

public class AuthValidator implements ServicePipelineMember {

	public static final String KEY = "/service/";
	
	public static final String ID = "auth-validator";
	
	private final ContextWrapper wrapper;
	
	public AuthValidator() {
		wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
		if (wrapper == null) {
			throw Exceptions.get(MintServiceException.class, "No implementation for ContextWrapper was found.");
		}
	}
	
	@Override
	public String id() {
		return ID;
	}


	@Override
	public void doValidate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintServiceException {
		String uri = req.getRequestURI();
		if (!uri.contains(KEY)) {
			return;
		}
		if (context == null) {
			throw new MintServiceException("User does not have context.");
		}
		roleValidation(req, resp, context);
	}

	private void roleValidation(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintServiceException {
		if (CollectionUtils.isEmpty(ServiceContext.supportedRoleIds)) {
			return; // if service has no role limitations
		}
		Set<Long> supportedRoleIds = context.getRoleIds();
		if (CollectionUtils.isEmpty(supportedRoleIds)) {
			throw new MintServiceException("Invalid user request, cannot find any role information.");
		}
		if (!ServiceContext.supportedRoleIds.stream().anyMatch(i -> supportedRoleIds.contains(i))) {
			throw Exceptions.get(MintServiceException.class, 
					"User request is illegal. Service %s just supports role id %s", 
					ServiceContext.id, Joiner.on(",").join(ServiceContext.supportedRoleIds));
		}
	}
	
}
