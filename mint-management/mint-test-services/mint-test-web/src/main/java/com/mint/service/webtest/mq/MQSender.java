package com.mint.service.webtest.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;

import com.mint.service.mq.annotation.support.RabbitComponent;
import com.mint.service.mq.common.MQRole;
import com.mint.service.mq.support.rabbit.RabbitMQSender;

@RabbitComponent(exchangeKey="extest", queueKey = "qtest", role = MQRole.SENDER)
public class MQSender extends RabbitMQSender<String> {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	protected RabbitTemplate getTemplate() {
		return rabbitTemplate;
	}

	@Override
	protected ConfirmCallback getConfirmCallback() {
		return null;
	}

	@Override
	protected ReturnCallback getReturnCallback() {
		return null;
	}

}
