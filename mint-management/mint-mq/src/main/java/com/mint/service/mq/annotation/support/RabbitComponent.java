package com.mint.service.mq.annotation.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import com.mint.service.mq.annotation.MintMQComponent;
import com.mint.service.mq.common.MQRole;
import com.mint.service.mq.support.rabbit.RabbitStyle;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MintMQComponent
public @interface RabbitComponent {
	
	@AliasFor(annotation = MintMQComponent.class, attribute = "value")
	String beanName() default "";
	
	RabbitStyle style() default RabbitStyle.DIRECT;
	
	String exchangeKey();
	
	String routeKey() default "";
	
	String queueKey();

	MQRole role();
	
}
