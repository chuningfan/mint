package com.mint.service.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mint.service.cache.support.redis.RedisMessageListener;

/**
 * 用于使用redis作为MQ服务的情况
 * @see {@link RedisMessageListener}
 * 
 * @author ningfanchu
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ListenTo {
	String value();
}
