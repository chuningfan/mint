package com.mint.service.exception;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mint.common.dto.web.WebResponse;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.common.exception.advice.ExceptionDataProcessor;
import com.mint.common.thread.ThreadPoolExecutor;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;

@ControllerAdvice(basePackages="com.mint")
public class MintExceptionGlobalManager {
	
	private ExceptionDataProcessor processor;
	
	private final ThreadPoolExecutor exe;
	
	public MintExceptionGlobalManager() {
		try {
			processor = CommonServiceLoader.getSingleService(ExceptionDataProcessor.class, ServiceContext.beanFactory);
		} catch (MintException e) {
			if (e.getErrorCode() == Error.IMPLEMENTATION_NOT_FOUND_ERROR.getCode()) {
				processor = null;
			} else {
				throw e;
			}
		}
		int processorCount = Runtime.getRuntime().availableProcessors();
		exe = new ThreadPoolExecutor(processorCount, processorCount << 1, 3 * 1000, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(100), Executors.defaultThreadFactory(), new java.util.concurrent.ThreadPoolExecutor.AbortPolicy());
	}
	
	@ResponseBody
    @ExceptionHandler(value = MintException.class)
    public WebResponse<Object> myExceptionHandler(MintException e){
        if (processor != null) {
        	exe.submit(() -> {
        		processor.process(e);
        	});
        }
        return new WebResponse<Object>(e);
    }
	
}
