package com.mint.common.exception.impl;

import com.mint.common.exception.ErrorMessageProvider;

public class ErrorMessageProviderForUS implements ErrorMessageProvider {

	private ErrorMessageProviderForUS(){}
	
	private static final class ErrorMessageProviderForUSHolder {
		private static final ErrorMessageProviderForUS INSTANCE = new ErrorMessageProviderForUS();
	}
	
	public static final ErrorMessageProviderForUS get() {
		return ErrorMessageProviderForUSHolder.INSTANCE;
	}
	
	@Override
	public String getMessage(Integer errorCode) {
		switch (errorCode) {
		case -1: //INTER_ERROR
			return "Server internal error";
		case -2: //READ_TIME_OUT_ERROR
			return "Time out for waiting response";
		case -3: //CONNECTION_ERROR
			return "Cannot connect to target server";
		case -4: //NO_DATA_FOUND_ERROR
			return "Cannot find target data";
		case -5: //UNEXPECT_DATA_ERROR
			return "Unexpect data";
		case -6: //URL_NOT_FOUND_ERROR
			return "URL is not available";
		case -7: //ILLEGAL_PARAM_ERROR
			return "Parameter(s) not supported";
		case -8: //VICOUS_REQ_ERROR
			return "Vicious request(s)";
		case -9: //INVALID_CONTEXT_ERROR
			return "Illegal user";
		case -10: //IMPLEMENTATION_NOT_FOUND_ERROR
			return "API/SPI has no implementation";
		case -11: //UNSUPPORTED_ERROR
			return "Unsupported operation";
		case -12: //ROLE_DISALLOWED_ERROR
			return "Illegal role";
		default: //UNKNOWN_ERROR
			return "Unknown error";
		}
	}

}
