package com.mint.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MintException extends RuntimeException {

	private static final long serialVersionUID = -3833029340038377442L;
	
	private int errorCode;
	
	private Throwable exception;
	
	private String msg;
	
	MintException() {}
	
	private MintException(Error error, Lang lang, Throwable e, String msg) {
		this.msg = ErrorMessageResource.getMessage(error.getCode(), lang);
		this.errorCode = error.getCode();
		this.exception = e;
	}
	
	public static MintException getException(Error error, Lang lang, Throwable e) {
		lang = lang == null ? Lang.US : lang;
		return new MintException(error, lang, e, e == null ? null : e.getMessage());
	}
	
	public static MintException getException(Throwable e, Lang lang) {
		lang = lang == null ? Lang.US : lang;
		return new MintException(Error.INTER_ERROR, lang, e, e == null ? null : e.getMessage());
	}

	public int getErrorCode() {
		return errorCode;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	public String getMsg() {
		return msg;
	}

	public MintException setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public MintException setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}
	
}
