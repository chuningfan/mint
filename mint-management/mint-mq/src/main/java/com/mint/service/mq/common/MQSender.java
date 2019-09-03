package com.mint.service.mq.common;

public abstract class MQSender<D, T, R> {

	public R send0(D destination, T message, MQExceptionHandler<T, R> handler) {
		try {
			return send(destination, message);
		} catch (Exception e) {
			return handler.processIfException(message, e);
		}
	}
	
	protected abstract R send(D destination, T message) throws Exception;
	
}
