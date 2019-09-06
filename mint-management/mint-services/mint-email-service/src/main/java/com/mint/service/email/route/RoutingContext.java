package com.mint.service.email.route;

import java.util.List;

import org.assertj.core.util.Lists;

import com.mint.service.email.manager.EmailManager;

public class RoutingContext {
	
	private volatile RoutingStrategy strategy;
	
	private volatile List<EmailManager> senders = Lists.newArrayList();
	
	public RoutingContext(RoutingStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void addManager(EmailManager manager) {
		List<EmailManager> tempSenders = Lists.newArrayList(senders);
		tempSenders.add(manager);
		synchronized (this) {
			senders = tempSenders;
		}
	}
	
	public void removeManager(String key) {
		List<EmailManager> tempSenders = Lists.newArrayList(senders);
		tempSenders.removeIf(s -> key.equals(s.getKey()));
		synchronized (this) {
			senders = tempSenders;
		}
	}
	
	public EmailManager getManager() {
		return getManagerByStrategy();
	}
	
	private EmailManager getManagerByStrategy() {
		return null;
	}
	
}
