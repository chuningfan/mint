package com.mint.common.context;

public interface ContextWrapper {

	void storeIntoCache(String key, UserContext context) throws Exception;
	
	void deleteFromCache(String...keys) throws Exception;

	boolean validate(String key);
	
}
