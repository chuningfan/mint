package com.mint.service.mq.support.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;

import com.mint.service.mq.annotation.support.RabbitComponent;
import com.mint.service.mq.common.MQSender;

public abstract class RabbitMQSender<T> extends MQSender<ExchangeAndRoute, T>{

	protected abstract RabbitTemplate getTemplate();
	
	@Override
	protected void send(ExchangeAndRoute destination, T message) throws Exception {
		RabbitTemplate tmplt = getTemplate();
		ConfirmCallback confirmCallback = getConfirmCallback();
		if (null != confirmCallback) {
			tmplt.setConfirmCallback(confirmCallback);
		}
		ReturnCallback returnCallback = getReturnCallback();
		if (null != returnCallback) {
			tmplt.setReturnCallback(returnCallback);
		}
		tmplt.convertAndSend(destination.getExchangeName(), destination.getRouteName(), message);
	}
	
	protected abstract ConfirmCallback getConfirmCallback();
	protected abstract ReturnCallback getReturnCallback();
	
	public void send(T message) throws Exception {
		RabbitComponent rc = getClass().getAnnotation(RabbitComponent.class);
		ExchangeAndRoute xr = new ExchangeAndRoute();
		xr.setExchangeName(rc.exchangeKey());
		xr.setRouteName(rc.routeKey());
		xr.setQueueName(rc.queueKey());
		send(xr, message);
	}
	
	public void send(T message, ConfirmCallback confirmCallback) throws Exception {
		RabbitComponent rc = getClass().getAnnotation(RabbitComponent.class);
		ExchangeAndRoute xr = new ExchangeAndRoute();
		xr.setExchangeName(rc.exchangeKey());
		xr.setRouteName(rc.routeKey());
		xr.setQueueName(rc.queueKey());
		send(xr, message);
	}
	
}
