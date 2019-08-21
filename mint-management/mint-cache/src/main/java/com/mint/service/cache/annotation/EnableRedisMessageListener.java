package com.mint.service.cache.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.mint.service.cache.annotation.importer.EnableRedisMsgListenerImporter;
import com.mint.service.cache.support.redis.RedisMessageListener;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EnableRedisMsgListenerImporter.class)
public @interface EnableRedisMessageListener {
	
	Class<? extends RedisMessageListener>[] listeners();
	
}
