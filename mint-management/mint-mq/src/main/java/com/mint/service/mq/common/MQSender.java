package com.mint.service.mq.common;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;

public abstract class MQSender<D, T> {

	public void send(D destination, T message, MQExceptionHandler<T> handler, ConfirmCallback confirmCallback) {
		try {
			send(destination, message, confirmCallback);
		} catch (Exception e) {
			handler.processIfException(message, e);
		}
	}
	
	protected abstract void send(D destination, T message, ConfirmCallback confirmCallback) throws Exception;
	
}
