package com.mint.service.exception;

public class AcquiredFailedException extends MintServiceException {
	
	private static final long serialVersionUID = 3072741396856737150L;

	public AcquiredFailedException(String errorMessage) {
		super(errorMessage);
	}
	
}
