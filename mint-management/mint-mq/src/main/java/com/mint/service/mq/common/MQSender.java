package com.mint.service.mq.common;

public abstract class MQSender<D, T> {

	protected void send(D destination, T message, MQExceptionHandler<T> handler) {
		try {
			send(destination, message);
		} catch (Exception e) {
			handler.processIfException(message, e);
		}
	}
	
	protected abstract void send(D destination, T message) throws Exception;
	
}
