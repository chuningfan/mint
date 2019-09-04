package com.mint.service.mq.support.rabbit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

	
	@Value("${spring.rabbitmq.listener.simple.acknowledge-mode}")
	private String ackMode;

	@Value("${spring.rabbitmq.publisher-returns}")
	private String returnIfFailed;
	
	public String getAckMode() {
		return ackMode;
	}

	public void setAckMode(String ackMode) {
		this.ackMode = ackMode;
	}

	public String getReturnIfFailed() {
		return returnIfFailed;
	}

	public void setReturnIfFailed(String returnIfFailed) {
		this.returnIfFailed = returnIfFailed;
	}
	
}
