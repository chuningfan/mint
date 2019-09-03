package com.mint.service.mq.support.rabbit;

public enum RabbitStyle {
	
	TOPIC("topic$"), FANOUT("fanout$"), DIRECT("direct$"), TTL("ttl$"); 
	
	private String prefix;

	private RabbitStyle(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}
	
	
}
