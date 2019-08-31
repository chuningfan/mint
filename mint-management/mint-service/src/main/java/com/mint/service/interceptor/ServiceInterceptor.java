package com.mint.service.interceptor;

import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.TokenHandler;
import com.mint.common.context.TokenThreadLocal;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.common.utils.HttpUtil;
import com.mint.service.context.ServiceContext;
import com.mint.service.pipeline.PipelineProvider;
import com.mint.service.pipeline.ServicePipelineMember;

public class ServiceInterceptor implements HandlerInterceptor {

	private final TokenHandler tokenHandler;

	private final PipelineProvider pipelineProvider;

	public ServiceInterceptor(PipelineProvider pipelineProvider) {
		tokenHandler = CommonServiceLoader.getSingleService(TokenHandler.class, ServiceContext.beanFactory);
		if (tokenHandler == null) {
			throw MintException.getException(Error.IMPLEMENTATION_NOT_FOUND_ERROR, null, null);
		}
		this.pipelineProvider = pipelineProvider;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		try {
			LinkedList<ServicePipelineMember<Throwable>> list = pipelineProvider.afterPipeline;
			if (list != null && !list.isEmpty()) {
				for (ServicePipelineMember<Throwable> m: list) {
					m.doValidate(request, response, ex);
				}
			}
		} catch (Exception e) {
			throw MintException.getException(e, null);
		} finally {
			TokenThreadLocal.remove();
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		try {
			LinkedList<ServicePipelineMember<ModelAndView>> list = pipelineProvider.postPipeline;
			if (list != null && !list.isEmpty()) {
				for (ServicePipelineMember<ModelAndView> m : list) {
					m.doValidate(request, response, modelAndView);
				}
			}
		} catch (Exception e) {
			throw MintException.getException(e, null);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = HttpUtil.getCookieValue(request, UserContextKeys.USER_CONTEXT);
		if (StringUtils.isEmpty(token)) {
			token = request.getHeader(UserContextKeys.USER_CONTEXT);
		}
		try {
			if (token != null && tokenHandler.validate(token)) {
				TokenThreadLocal.set(token);
			}
			LinkedList<ServicePipelineMember<String>> list = pipelineProvider.prePipeline;
			if (list != null && !list.isEmpty()) {
				for (ServicePipelineMember<String> m : list) {
					m.doValidate(request, response, token);
				}
			}
			return true;
		} catch (Exception e) {
			throw MintException.getException(e, null);
		}
	}

}
