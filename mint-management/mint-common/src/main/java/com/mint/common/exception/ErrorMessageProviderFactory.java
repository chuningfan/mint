package com.mint.common.exception;

import com.mint.common.exception.impl.ErrorMessageProviderForCN;
import com.mint.common.exception.impl.ErrorMessageProviderForUS;

public class ErrorMessageProviderFactory {
	
	public static ErrorMessageProvider get(Lang lang) {
		switch (lang) {
			case CN: return ErrorMessageProviderForCN.get();
			default: return ErrorMessageProviderForUS.get();
		}
	}
	
}
