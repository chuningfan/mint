package com.mint.service.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mint.common.dto.web.WebResponse;
import com.mint.common.exception.MintException;
import com.mint.common.exception.advice.ExceptionDataProcessor;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;

@ControllerAdvice(basePackages="com.mint")
public class MintExceptionGlobalManager {
	
	private ExceptionDataProcessor processor;
	
	public MintExceptionGlobalManager() {
		processor = CommonServiceLoader
				.getMultipleServices(ExceptionDataProcessor.class, ServiceContext.beanFactory)
				.stream()
				.filter(p -> p.getClass() == ServiceContext.exceptionDataProcessor)
				.findFirst().get();
				
	}
	
	@ResponseBody
    @ExceptionHandler(value = Exception.class)
    public WebResponse<Object> exceptionHandler(Exception e) {
		MintException exc = null;
		if (!(e instanceof MintException)) {
			exc = MintException.getException(e, null);
		} else {
			exc = (MintException) e;
		}
		if (processor != null) {
			processor.process(exc);
		}
        return new WebResponse<Object>(exc);
    }
	
}
