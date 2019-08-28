package com.mint.service.security.sault;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.mint.common.exception.MintException;

public class Base64Util {
	
	public static String encode(String string) {
        Base64.Encoder base64Encoder = Base64.getEncoder();
        try {
			return base64Encoder.encodeToString(string.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw MintException.getException(e, null);
		}
	}
	
	public static String decode(String string) {
        Base64.Decoder base64Decoder = Base64.getDecoder();
        try {
			return new String(base64Decoder.decode(string), "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw MintException.getException(e, null);
		}
	}
	
}
