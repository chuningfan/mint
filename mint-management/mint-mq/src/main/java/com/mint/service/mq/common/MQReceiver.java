package com.mint.service.mq.common;

import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

public abstract class MQReceiver<T> implements ChannelAwareMessageListener {

	public void onMessage(T message, MQExceptionHandler<T> handler) {
		try {
			onMessage(message);
		} catch (Exception e) {
			if (handler != null) {
				handler.processIfException(message, e);
			}
		}
	}
	
	protected abstract void onMessage(T message) throws Exception;
	
	public abstract MQExceptionHandler<T> getExceptionHandler();
	
}
