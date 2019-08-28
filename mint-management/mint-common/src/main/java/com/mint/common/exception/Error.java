package com.mint.common.exception;

public enum Error {
	
	INTER_ERROR(-1),  //服务器运行错误
	READ_TIME_OUT_ERROR(-2), // 等待响应超时错误
	CONNECTION_ERROR(-3), // 连接错误
	NO_DATA_FOUND_ERROR(-4), // 无数据错误
	UNEXPECT_DATA_ERROR(-5), // 返回结果集与预期不一致错误
	URL_NOT_FOUND_ERROR(-6), // 404错误
	ILLEGAL_PARAM_ERROR(-7), // 传参错误
	VICOUS_REQ_ERROR(-8), // 恶意请求错误
	INVALID_CONTEXT_ERROR(-9), // 非法用户上下文错误
	IMPLEMENTATION_NOT_FOUND_ERROR(-10), // 无实现错误
	UNSUPPORTED_ERROR(-11), // 非支持操作错误
	UNKNOWN_ERROR(-12) // 未知错误
	;
	
	private int code;
	
	private Error(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
