package com.mint.service.mq.common;

public interface MQExceptionHandler<T> {
	
	void processIfException(T message, Exception e);
	
}
