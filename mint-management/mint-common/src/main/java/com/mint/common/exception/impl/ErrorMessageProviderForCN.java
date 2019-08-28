package com.mint.common.exception.impl;

import com.mint.common.exception.ErrorMessageProvider;

public class ErrorMessageProviderForCN implements ErrorMessageProvider  {

	private ErrorMessageProviderForCN(){}
	
	private static final class ErrorMessageProviderForCNHolder {
		private static final ErrorMessageProviderForCN INSTANCE = new ErrorMessageProviderForCN();
	}
	
	public static final ErrorMessageProviderForCN get() {
		return ErrorMessageProviderForCNHolder.INSTANCE;
	}
	
	@Override
	public String getMessage(Integer errorCode) {
		switch (errorCode) {
		case -1: //INTER_ERROR
			return "服务器内部错误";
		case -2: //READ_TIME_OUT_ERROR
			return "等待超时";
		case -3: //CONNECTION_ERROR
			return "连接超时";
		case -4: //NO_DATA_FOUND_ERROR
			return "未找到目标数据";
		case -5: //UNEXPECT_DATA_ERROR
			return "数据异常";
		case -6: //URL_NOT_FOUND_ERROR
			return "URL无法访问";
		case -7: //ILLEGAL_PARAM_ERROR
			return "参数错误";
		case -8: //VICOUS_REQ_ERROR
			return "恶意请求攻击";
		case -9: //INVALID_CONTEXT_ERROR
			return "非法用户身份";
		case -10: //IMPLEMENTATION_NOT_FOUND_ERROR
			return "未找到方法实现";
		case -11: //UNSUPPORTED_ERROR
			return "不支持当前操作";
		default: //UNKNOWN_ERROR
			return "未知错误";
		}
	}

}
