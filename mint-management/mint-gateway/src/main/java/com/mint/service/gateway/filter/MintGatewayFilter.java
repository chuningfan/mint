package com.mint.service.gateway.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.gateway.meta.GatewayServiceMetadataProvider;
import com.mint.service.pipeline.PipelineWorker;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Configuration
public class MintGatewayFilter extends ZuulFilter {

	@Autowired
	private PipelineWorker pipelineWorker;
	
	@Override
	public Object run() throws ZuulException {
		RequestContext rc = RequestContext.getCurrentContext();
		HttpServletRequest req = rc.getRequest();
		HttpServletResponse resp = rc.getResponse();
		if (GatewayServiceMetadataProvider.UNCHECKED_URI.contains(req.getRequestURI())) {
			return null;
		}
		UserContext context = null;
		ContextWrapper wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
		if (wrapper != null) {
			try {
				context = wrapper.getFromReq(req);
				if (context == null) {
					rc.setResponseStatusCode(403);
					rc.setSendZuulResponse(false);
					rc.setResponseBody("Error: No context was found in request.");	
					return null;
				}
				pipelineWorker.doProcess(req, resp, context);
			} catch (Exception e) {
				rc.setResponseStatusCode(403);
				rc.setSendZuulResponse(false);
				rc.setResponseBody(e.getMessage());	
			}
		} else {
			throw new ZuulException("No context wrapper implementation.", 500, "For Detail please see SPI [ContextWrapper]");
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
