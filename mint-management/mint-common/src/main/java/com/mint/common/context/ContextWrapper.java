package com.mint.common.context;

import java.util.concurrent.TimeUnit;

public interface ContextWrapper {

	void storeIntoCache(String key, UserContext context, long expireTime, TimeUnit unit) throws Exception;
	
	void deleteFromCache(String...keys) throws Exception;

	boolean validate(String key);
	
}
