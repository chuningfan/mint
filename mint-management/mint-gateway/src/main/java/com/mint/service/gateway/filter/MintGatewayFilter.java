package com.mint.service.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.mint.service.pipeline.PipelineWorker;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Configuration
public class MintGatewayFilter extends ZuulFilter {

	@Autowired
	private PipelineWorker pipelineWorker;
	
	@Value("${mint.service.url.login}")
	private String loginUrl;
	
	@Override
	public Object run() throws ZuulException {
		RequestContext rc = RequestContext.getCurrentContext();
		HttpServletRequest req = rc.getRequest();
		HttpServletResponse resp = rc.getResponse();
		try {
			pipelineWorker.doProcess(req, resp, null);
		} catch (Exception e) {
			rc.setResponseStatusCode(403);
			rc.setSendZuulResponse(false);
			rc.setResponseBody(e.getMessage());	
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
