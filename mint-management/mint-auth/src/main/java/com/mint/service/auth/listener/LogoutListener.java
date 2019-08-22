package com.mint.service.auth.listener;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class LogoutListener implements Observer {

	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public void update(Observable o, Object arg) {
		String redisKey = (String) arg;
		redisHelper.removeIfPresent(redisKey);
	}

}
