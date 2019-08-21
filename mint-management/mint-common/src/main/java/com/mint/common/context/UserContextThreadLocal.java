package com.mint.common.context;

public class UserContextThreadLocal {

	private static final ThreadLocal<UserContext> userContextPool = new ThreadLocal<UserContext>();
	
	public static void set(UserContext context) {
		userContextPool.set(context);
	}

	public static UserContext get() {
		return userContextPool.get();
	}
	
	public static void remove() {
		userContextPool.remove();
	}
	
}
