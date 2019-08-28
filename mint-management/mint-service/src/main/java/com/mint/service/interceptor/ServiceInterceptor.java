package com.mint.service.interceptor;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.context.UserContextThreadLocal;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;
import com.mint.service.pipeline.PipelineProvider;
import com.mint.service.pipeline.ServicePipelineMember;

public class ServiceInterceptor implements HandlerInterceptor {

	private final ContextWrapper wrapper;

	private final PipelineProvider pipelineProvider;

	public ServiceInterceptor(PipelineProvider pipelineProvider) {
		wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
		if (wrapper == null) {
			throw MintException.getException(Error.IMPLEMENTATION_NOT_FOUND_ERROR, null, null);
		}
		this.pipelineProvider = pipelineProvider;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		try {
			LinkedList<ServicePipelineMember> list = pipelineProvider.afterPipeline;
			if (list != null && !list.isEmpty()) {
				for (ServicePipelineMember m: list) {
					m.doValidate(request, response, UserContextThreadLocal.get());
				}
			}
		} catch (Exception e) {
			throw MintException.getException(e, null);
		} finally {
			UserContextThreadLocal.remove();
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		try {
			LinkedList<ServicePipelineMember> list = pipelineProvider.postPipeline;
			if (list != null && !list.isEmpty()) {
				for (ServicePipelineMember m : list) {
					m.doValidate(request, response, UserContextThreadLocal.get());
				}
			}
		} catch (Exception e) {
			throw MintException.getException(e, null);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		UserContext context = wrapper.getFromReq(request);
		try {
			if (context != null) {
				UserContextThreadLocal.set(context);
			}
			LinkedList<ServicePipelineMember> list = pipelineProvider.prePipeline;
			if (list != null && !list.isEmpty()) {
				for (ServicePipelineMember m : list) {
					m.doValidate(request, response, UserContextThreadLocal.get());
				}
			}
			return true;
		} catch (Exception e) {
			throw MintException.getException(e, null);
		}
	}

}
