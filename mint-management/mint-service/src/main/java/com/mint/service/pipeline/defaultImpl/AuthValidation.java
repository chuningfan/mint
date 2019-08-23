package com.mint.service.pipeline.defaultImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;

import com.google.common.base.Joiner;
import com.mint.common.context.UserContext;
import com.mint.service.context.ServiceContext;
import com.mint.service.exception.Exceptions;
import com.mint.service.exception.MintServiceException;
import com.mint.service.pipeline.ServicePipelineMember;

/**
 * 本类旨在校验请求者是否有本服务访问权限，可在网关服务直接校验，也可以在中台子服务各自校验
 * 
 * @author ningfanchu
 *
 */
public class AuthValidation implements ServicePipelineMember {

	public static final String ID = "mint-service-auth";
	
	@Override
	public String id() {
		return ID;
	}

	@Override
	public void validate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintServiceException {
		if (CollectionUtils.isEmpty(ServiceContext.supportedRoleIds)) {
			return;
		}
		List<Long> supportedRoleIds = context.getRoleIds();
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
