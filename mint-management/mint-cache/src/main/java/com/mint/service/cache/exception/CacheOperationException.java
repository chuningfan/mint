package com.mint.service.cache.exception;

public class CacheOperationException extends RuntimeException {

	private static final long serialVersionUID = -8822242287247365134L;
	
	public CacheOperationException(String message) {
		super(message);
	}

	public CacheOperationException(Throwable thr) {
		super(thr);
	}
	
}
