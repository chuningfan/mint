package com.mint.service.email.annotation.importer;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mint.service.email.manager.EmailManager;
import com.mint.service.email.route.RoutingStrategy;

public class RoutingContext {
	
	private volatile RoutingStrategy strategy;
	
	private volatile List<EmailManager> senders = Lists.newArrayList();
	
	private volatile int hashFactor = 0;
	
	private final Random ran = new Random();
	
	private Integer next = 0;
	
	public RoutingContext(RoutingStrategy strategy) {
		this.strategy = strategy;
	}
	
	void addManager(EmailManager manager) {
		List<EmailManager> tempSenders = Lists.newArrayList(senders);
		tempSenders.add(manager);
		synchronized (this) {
			senders = tempSenders;
			hashFactor++;
		}
	}
	
	void removeManager(String key) {
		List<EmailManager> tempSenders = Lists.newArrayList(senders);
		tempSenders.removeIf(s -> key.equals(s.getKey()));
		synchronized (this) {
			senders = tempSenders;
			hashFactor--;
		}
	}
	
	public EmailManager getManager() {
		return getManagerByStrategy();
	}
	
	private EmailManager getManagerByStrategy() {
		switch (strategy) {
		case LOOP: 
			return senders.get(getNextPosition());
		case RANDOM: 
			return senders.get(ran.nextInt(hashFactor));
		default: // HASH 
			return senders.get((int)(System.nanoTime()%hashFactor));
		}
	}
	
	private int getNextPosition() {
		synchronized (next) {
			if (next < hashFactor - 1) {
				return ++next;
			} else {
				next = 0;
				return next;
			}
		}
	}
	
}
