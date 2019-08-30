package com.mint.service.gateway.filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
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

	@Autowired
	private ObjectMapper objectMapper;
	
	private final AuthValidator authValidator;
	
	@Autowired
	private BlackListValidation blackListValidation;
	
	public MintGatewayFilter() {
		this.wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
		if (wrapper == null) {
			MintException.getException(Error.IMPLEMENTATION_NOT_FOUND_ERROR, null, null);
		}
		authValidator = new AuthValidator();
	}
	
	@Override
	public Object run() throws ZuulException {
		RequestContext rc = RequestContext.getCurrentContext();
		HttpServletRequest req = rc.getRequest();
		HttpServletResponse resp = rc.getResponse();
		UserContext context;
		blackListValidation.doValidate(req, resp, null);
		String uri = req.getRequestURI();
		if (uri.contains(AuthValidator.KEY)) {
			try {
				context = wrapper.getFromReq(req);
				if (context != null) {
					ModifyHttpServletRequestWrapper wrapper = new ModifyHttpServletRequestWrapper(req);
					wrapper.putHeader(UserContextKeys.USER_CONTEXT, objectMapper.writeValueAsString(context));
					rc.setRequest(wrapper); // set req
					authValidator.doValidate(wrapper, resp, context);
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

	private static class ModifyHttpServletRequestWrapper extends HttpServletRequestWrapper {
		 
	    private Map<String, String> customHeaders;
	 
	    public ModifyHttpServletRequestWrapper(HttpServletRequest request) {
	        super(request);
	        this.customHeaders = Maps.newHashMap();
	    }
	 
	    public void putHeader(String name, String value) {
	        this.customHeaders.put(name, value);
	    }
	 
	    public String getHeader(String name) {
	        String value = this.customHeaders.get(name);
	        if (value != null) {
	            return value;
	        }
	        return ((HttpServletRequest) getRequest()).getHeader(name);
	    }
	 
	    public Enumeration<String> getHeaderNames() {
	        Set<String> set = Sets.newHashSet(customHeaders.keySet());
	        Enumeration<String> enumeration = ((HttpServletRequest) getRequest()).getHeaderNames();
	        while (enumeration.hasMoreElements()) {
	            String name = enumeration.nextElement();
	            set.add(name);
	        }
	        return Collections.enumeration(set);
	    }
	 
	}
	
	
}
