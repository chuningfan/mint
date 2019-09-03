package com.mint.service.mq.common;

public abstract class MQReceiver<T, R> {

	public R onMessage0(T message, MQExceptionHandler<T, R> handler) {
		try {
			return onMessage(message);
		} catch (Exception e) {
			return handler.processIfException(message, e);
		}
	}
	
	protected abstract R onMessage(T message) throws Exception;
	
}
