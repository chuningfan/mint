package com.mint.service.mq.support.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.service.mq.common.MQSender;

@Component
public class RabbitMQSender extends MQSender<ExchangeAndRoute, Object>{

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	protected void send(ExchangeAndRoute destination, Object message, ConfirmCallback confirmCallback) throws Exception {
		if (null != confirmCallback) {
			rabbitTemplate.setConfirmCallback(confirmCallback);
		}
		rabbitTemplate.convertAndSend(destination.getExchangeName(), destination.getRouteName(), message);
	}

}
