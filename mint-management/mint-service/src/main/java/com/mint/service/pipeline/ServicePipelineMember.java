package com.mint.service.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;

import com.mint.common.context.UserContext;
import com.mint.common.exception.MintException;

/**
 * 
 * Pipeline 成员，实现 {@link #id()}, {@link #validate(HttpServletRequest, HttpServletResponse, UserContext)}
 * 
 * @author ningfanchu
 *
 */
public interface ServicePipelineMember<T> {
	
	/*
	 * 每一个member需要有自己的唯一id标志
	 */
	String id();
	
	void doValidate(HttpServletRequest req, HttpServletResponse resp, @Nullable T t) throws MintException;
	
}
