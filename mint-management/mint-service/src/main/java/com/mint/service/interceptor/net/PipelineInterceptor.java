package com.mint.service.interceptor.net;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mint.common.context.ContextWrapper;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.exception.MintServiceException;
import com.mint.service.interceptor.MintInterceptor;
import com.mint.service.pipeline.PipelineWorker;

public class PipelineInterceptor extends MintInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(PipelineInterceptor.class);
	
	private final ContextWrapper wrapper;
	
	public PipelineInterceptor() {
		wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		PipelineWorker pipelineWorker = beanFactory.getBean(PipelineWorker.class);
		if (ServiceContext.validateContext) {
			if (wrapper == null) {
				response.sendError(500, "No context SPI implementation was found!");
				return false;
			}
		}
		try {
			pipelineWorker.doProcess(request, response, 
					ServiceContext.userContextPool.get());
		} catch (MintServiceException e) {
			response.sendError(500, e.getMessage());
			LOG.error(e.getMessage());
			return false;
		}
		return true;
	}
	
}
