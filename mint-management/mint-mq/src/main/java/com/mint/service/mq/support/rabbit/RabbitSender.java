package com.mint.service.mq.support.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.service.mq.common.MQSender;

@Component
public class RabbitSender extends MQSender<String, Object>{

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Override
	protected void send(String destination, Object message) throws Exception {
		amqpTemplate.convertAndSend(destination, message);
	}

}
