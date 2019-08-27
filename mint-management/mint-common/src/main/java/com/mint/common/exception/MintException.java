package com.mint.common.exception;

public class MintException extends RuntimeException {

	private static final long serialVersionUID = -3833029340038377442L;
	
	private Error error;
	
	public MintException(Error e) {
		super(e.getException());
		this.error = e;
	}

	public Error getError() {
		return error;
	}
	
	public static void throwException(Error error) {
		throw new MintException(error);
	}
	
}
