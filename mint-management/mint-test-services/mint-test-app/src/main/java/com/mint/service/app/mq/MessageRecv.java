package com.mint.service.app.mq;

import com.mint.service.mq.annotation.support.RabbitComponent;
import com.mint.service.mq.common.MQExceptionHandler;
import com.mint.service.mq.common.MQReceiver;
import com.mint.service.mq.common.MQRole;

@RabbitComponent(exchangeKey = "extest", queueKey = "qtest", role = MQRole.LISTENER)
public class MessageRecv extends MQReceiver<String> {

	protected MessageRecv(Class<String> tClass) {
		super(String.class);
	}

	@Override
	protected void onMessage(String message) throws Exception {
		System.out.println("接收到消息：" + message);
	}

	@Override
	public MQExceptionHandler<String> getExceptionHandler() {
		MQExceptionHandler<String> h = new MQExceptionHandler<String>() {
			@Override
			public void processIfException(String message, Exception e) {
				System.out.println(e.getMessage());
			}
		};
		return h;
	}

}
