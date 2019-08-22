package com.mint.service.auth.listener;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.common.context.UserContext;
import com.mint.service.cache.support.redis.RedisHelper;

@Component
public class LoginListener implements Observer {

	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public void update(Observable o, Object arg) {
		UserContext context = (UserContext) arg;
		
	}

}
