package com.mint.service.mq.common;

public interface MQExceptionHandler<T, R> {
	
	R processIfException(T message, Exception e);
	
}
