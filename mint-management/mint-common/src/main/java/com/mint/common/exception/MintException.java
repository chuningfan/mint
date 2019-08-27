package com.mint.common.exception;

public class MintException extends RuntimeException {

	private static final long serialVersionUID = -3833029340038377442L;
	
	public MintException(Error e, Lang lang) {
		super(ErrorMessageResource.getMessage(e.getCode(), lang));
	}
	
	public static void throwException(Error error, Lang lang) {
		throw new MintException(error, lang);
	}
	
}
