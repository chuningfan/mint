package com.mint.service.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.UserContext;
import com.mint.common.utils.HttpUtil;
import com.mint.service.exception.MintServiceException;
import com.mint.service.pipeline.ServicePipelineMember;
import com.mint.service.security.exception.ViciousRequestException;
import com.mint.service.security.guard.BlackListConcierge;

@Component
public class BlackListValidation implements ServicePipelineMember {

	private static final Logger LOG = LoggerFactory.getLogger(BlackListValidation.class);
	
	public static final String ID = "mint-blacklist-validator";
	
	@Autowired
	private BlackListConcierge blackListConcierge;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public String id() {
		return ID;
	}

	@Override
	public void validate(HttpServletRequest req, HttpServletResponse resp, UserContext context)
			throws MintServiceException {
		try {
			blackListConcierge.validate(req, resp, context);
		} catch (Throwable e) {
			String ip = HttpUtil.getIpAddress(req);
			if (e instanceof ViciousRequestException) {
				LOG.error("received vicious request from {}", ip);
				// TODO lock user
				restTemplate.postForEntity("http://user-service/service/lock", context.getUserId(), HttpStatus.class);
				HttpUtil.deleteCookiesByKey(req, resp, UserContextKeys.USER_CONTEXT);
			} else {
				LOG.error("When filtering black list, an error was encountered {}.", e.getMessage());
			}
			throw new  MintServiceException(e);
		}
	}

}
