package com.mint.common.exception;

public enum Error {
	
	INTER_ERROR,  //服务器运行错误
	READ_TIME_OUT_ERROR, // 等待响应超时错误
	CONNECTION_ERROR, // 连接错误
	NO_DATA_FOUND_ERROR, // 无数据错误
	UNEXPECT_DATA_ERROR, // 返回结果集与预期不一致错误
	URL_NOT_FOUND_ERROR, // 404错误
	ILLEGAL_PARAM_ERROR, // 传参错误
	VICOUS_REQ_ERROR, // 恶意请求错误
	INVALID_CONTEXT_ERROR; // 非法用户上下文错误
	
	private String message;
	
	private Throwable exception;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
}
