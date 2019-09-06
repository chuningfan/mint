package com.mint.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MintException extends RuntimeException {

	private static final long serialVersionUID = -3833029340038377442L;
	
	private int errorCode;
	
	MintException() {}
	
	private MintException(Error error, Lang lang, Throwable e, String msg) {
		super(msg, e);
		this.errorCode = error.getCode();
	}
	
	private MintException(Error error, Throwable e, String msg) {
		super(msg, e);
		this.errorCode = error.getCode();
	}
	
	public static MintException getException(Error error, Throwable e, String msg, Object...args) {
		if (args != null && args.length > 0) {
			msg = String.format(msg, args);
		}
		return new MintException(error, e, msg);
	}
	
	public static MintException getException(Error error, Lang lang, Throwable e) {
		lang = lang == null ? Lang.US : lang;
		return new MintException(error, lang, e, ErrorMessageResource.getMessage(error.getCode(), lang));
	}
	
	public static MintException getException(Throwable e, Lang lang) {
		lang = lang == null ? Lang.US : lang;
		return new MintException(Error.INTER_ERROR, lang, e, e == null ? null : e.getMessage());
	}

	public int getErrorCode() {
		return errorCode;
	}

	public MintException setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	
}
