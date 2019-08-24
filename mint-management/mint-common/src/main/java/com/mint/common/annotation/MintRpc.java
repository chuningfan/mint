package com.mint.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mint.common.enums.ProtocolType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MintRpc {
	
	ProtocolType protocolType() default ProtocolType.HTTP;
	
	String serviceName();
	
	String requestMapping() default "/service";
	
	boolean longConnection() default false;
	
}


