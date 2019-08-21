package com.mint.service.cache.annotation;

import com.mint.service.cache.annotation.enums.RedisDataType;
import com.mint.service.cache.annotation.enums.RedisOpsType;

public @interface RedisOps {
	
	String key();
	
	String keyIn() default "";
	
	RedisOpsType type();
	
	boolean failFast() default true;
	
	RedisDataType dataType() default RedisDataType.STRING;
	
}
