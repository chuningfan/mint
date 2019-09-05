package com.mint.service.mq.common;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;

public abstract class MQReceiver<T> implements ChannelAwareMessageListener {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
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
	
	protected abstract Class<T> getGenericClass();
	
	public abstract MQExceptionHandler<T> getExceptionHandler();

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (IOException e) {
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			return;
		}
		byte[] bodyBytes = message.getBody();
		T body = mapper.readValue(bodyBytes, getGenericClass());
		onMessage(body, getExceptionHandler());
	}
	
}
