package com.mint.common.exception;

public class MintException extends RuntimeException {

	private static final long serialVersionUID = -3833029340038377442L;
	
	private int errorCode;
	
	private Throwable e;
	
	private String msg;
	
	private MintException(Error error, Lang lang, Throwable e, String msg) {
		super(ErrorMessageResource.getMessage(error.getCode(), lang));
		this.errorCode = error.getCode();
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

	public Throwable getE() {
		return e;
	}

	public MintException setE(Throwable e) {
		this.e = e;
		return this;
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
