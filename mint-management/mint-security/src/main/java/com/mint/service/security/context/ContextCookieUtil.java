package com.mint.service.security.context;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.mint.service.security.sault.Base64Util;

public class ContextCookieUtil {
	
	public static String createCookieValue(String val) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String cookieValue = Base64Util.encode(new StringBuilder(val).append(",").append(System.currentTimeMillis()).toString());
		return cookieValue;
	}
	
	public static String getCookieRealValue(String saultedValue) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return Base64Util.decode(saultedValue).split(",")[0];
	}
	
}
