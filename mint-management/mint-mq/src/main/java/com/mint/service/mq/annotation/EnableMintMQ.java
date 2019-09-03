package com.mint.service.mq.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.mint.service.mq.annotation.importer.MintMQImporter;
import com.mint.service.mq.common.SupportedMQ;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MintMQImporter.class)
@Documented
public @interface EnableMintMQ {
	
	SupportedMQ[] use();
	
}
