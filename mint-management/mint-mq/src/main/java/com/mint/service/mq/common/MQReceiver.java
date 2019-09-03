package com.mint.service.mq.common;

public abstract class MQReceiver {

	public void onMessage(Object message, MQExceptionHandler<Object> handler) {
		try {
			onMessage(message);
		} catch (Exception e) {
			handler.processIfException(message, e);
		}
	}
	
	protected abstract void onMessage(Object message) throws Exception;
	
}
