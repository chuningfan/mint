package com.mint.common.context;

public class TokenThreadLocal {

	private static final ThreadLocal<String> tokenPool = new ThreadLocal<String>();
	
	public static void set(String token) {
		tokenPool.set(token);
	}

	public static String get() {
		return tokenPool.get();
	}
	
	public static void remove() {
		tokenPool.remove();
	}
	
}
