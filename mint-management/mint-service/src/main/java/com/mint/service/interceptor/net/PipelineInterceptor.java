package com.mint.service.interceptor.net;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mint.common.context.UserContextThreadLocal;
import com.mint.service.exception.MintServiceException;
import com.mint.service.interceptor.MintInterceptor;
import com.mint.service.pipeline.PipelineWorker;

public class PipelineInterceptor extends MintInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(PipelineInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		PipelineWorker pipelineWorker = beanFactory.getBean(PipelineWorker.class);
		try {
			pipelineWorker.doProcess(request, response, 
					UserContextThreadLocal.get());
		} catch (MintServiceException e) {
			response.sendError(500, e.getMessage());
			LOG.error(e.getMessage());
			return false;
		}
		return true;
	}
	
}
