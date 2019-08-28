package com.mint.service.response;

import com.mint.common.exception.MintException;

public class WebResponse<T> {
	
	private T data;
	
	private MintException exception;

	public WebResponse(T data) {
		this.data = data;
	}
	
	public WebResponse(MintException exception) {
		this.exception = exception;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public MintException getException() {
		return exception;
	}

	public void setException(MintException exception) {
		this.exception = exception;
	}
	
}
