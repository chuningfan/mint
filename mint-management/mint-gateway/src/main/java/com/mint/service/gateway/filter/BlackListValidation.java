package com.mint.service.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.HttpUtil;
import com.mint.service.pipeline.ServicePipelineMember;
import com.mint.service.security.guard.BlackListConcierge;


public class BlackListValidation implements ServicePipelineMember<String> {

	private static final Logger LOG = LoggerFactory.getLogger(BlackListValidation.class);
	
	public static final String ID = "mint-blacklist-validator";
	
	private BlackListConcierge blackListConcierge;
	
	public BlackListValidation(BlackListConcierge blackListConcierge) {
		this.blackListConcierge = blackListConcierge;
	}
	
	@Override
	public String id() {
		return ID;
	}

	@Override
	public void doValidate(HttpServletRequest req, HttpServletResponse resp, String token) throws MintException {
		try {
			blackListConcierge.validate(req, resp);
		} catch (Throwable e) {
			MintException exc = (MintException) e;
			String ip = HttpUtil.getIpAddress(req);
			if (exc.getErrorCode() == Error.VICOUS_REQ_ERROR.getCode()) {
				if (StringUtils.isNoneBlank(token)) {
					LOG.error("received vicious request from {}", ip);
					// TODO lock user
					HttpUtil.deleteCookiesByKey(req, resp, UserContextKeys.USER_CONTEXT);
				}
			} else {
				LOG.error("When filtering black list, an error was encountered {}.", e.getMessage());
			}
			throw MintException.getException(e, null);
		}		
	}

}
