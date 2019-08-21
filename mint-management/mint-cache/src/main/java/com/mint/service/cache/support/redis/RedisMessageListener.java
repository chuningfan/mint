package com.mint.service.cache.support.redis;

import org.springframework.data.redis.connection.Message;

import com.mint.service.cache.annotation.ListenTo;

/**
 * 当使用redis作为MQ服务时，实现该接口，并注入Spring
 * 注意： 需要在具体实现类上使用{@link ListenTo}指定topic
 * 
 * @author ningfanchu
 *
 */
public interface RedisMessageListener {

	public void onMessage(Message msg) throws Throwable;
	
}
