package com.mint.service.security.context;

import com.mint.common.exception.MintException;
import com.mint.service.security.sault.Base64Util;

public class ContextCookieUtil {

	public static String createCookieValue(String val) throws MintException {
		String cookieValue = Base64Util
				.encode(new StringBuilder(val).append(",").append(System.currentTimeMillis()).toString());
		return cookieValue;
	}

	public static String getCookieRealValue(String saultedValue) throws MintException {
		return Base64Util.decode(saultedValue).split(",")[0];
	}

}
