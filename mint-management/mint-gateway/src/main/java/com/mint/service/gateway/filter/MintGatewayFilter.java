package com.mint.service.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.gateway.exception.GatewayException;
import com.mint.service.pipeline.pre.AuthValidator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Configuration
public class MintGatewayFilter extends ZuulFilter {

	private static final Logger LOG = LoggerFactory.getLogger(MintGatewayFilter.class);
	
	@Value("${mint.service.url.login}")
	private String loginUrl;
	
	private final ContextWrapper wrapper;
	
	public MintGatewayFilter() {
		this.wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
		if (wrapper == null) {
			new GatewayException("No context wrapper was found!");
		}
	}
	
	@Override
	public Object run() throws ZuulException {
		RequestContext rc = RequestContext.getCurrentContext();
		HttpServletRequest req = rc.getRequest();
		HttpServletResponse resp = rc.getResponse();
		String uri = req.getRequestURI();
		if (uri.contains(AuthValidator.KEY)) {
			UserContext context = null;
			try {
				context = wrapper.getFromReq(req);
				if (context == null) {
					resp.sendRedirect("");
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
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
