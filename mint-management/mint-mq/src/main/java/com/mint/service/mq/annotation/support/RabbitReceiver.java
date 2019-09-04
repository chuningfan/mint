package com.mint.service.mq.annotation.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mint.service.mq.annotation.MintReceiver;
import com.mint.service.mq.support.rabbit.RabbitStyle;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MintReceiver
public @interface RabbitReceiver {
	
	RabbitStyle style() default RabbitStyle.DIRECT;
	
	String exchangeKey();
	
	String routeKey();
	
	String queueKey();
	
}
