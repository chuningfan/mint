package com.mint.common.exception;

public class SPILoadingException extends RuntimeException {

	private static final long serialVersionUID = -3000589633697421897L;

	public SPILoadingException(String errorMessage) {
		super(errorMessage);
	}
	
}
