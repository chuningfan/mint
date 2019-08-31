package com.mint.service.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.TokenResolver;
import com.mint.common.utils.HttpUtil;
import com.mint.service.pipeline.pre.AuthValidator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Configuration
public class MintGatewayFilter extends ZuulFilter {

	private final AuthValidator authValidator;
	
	@Autowired
	private BlackListValidation blackListValidation;
	
	@Autowired
	private TokenResolver tokenResolver;
	
	public MintGatewayFilter() {
		authValidator = new AuthValidator();
	}
	
	@Override
	public Object run() throws ZuulException {
		RequestContext rc = RequestContext.getCurrentContext();
		HttpServletRequest req = rc.getRequest();
		HttpServletResponse resp = rc.getResponse();
		String token = HttpUtil.getCookieValue(req, UserContextKeys.USER_CONTEXT);
		blackListValidation.doValidate(req, resp, token);
		authValidator.doValidate(req, resp, token);
		tokenResolver.autoRefreshExpiration(token, req, resp);
//		if (StringUtils.isNotBlank(token)) {
//			ModifyHttpServletRequestWrapper wrapper = new ModifyHttpServletRequestWrapper(req);
//			wrapper.putHeader(UserContextKeys.USER_CONTEXT, token);
//			rc.setRequest(wrapper);
//		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	
}
