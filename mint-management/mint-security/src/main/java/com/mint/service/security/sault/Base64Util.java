package com.mint.service.security.sault;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Base64Util {
	
	public static String encode(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Base64.Encoder base64Encoder = Base64.getEncoder();
        return base64Encoder.encodeToString(string.getBytes("utf-8"));
	}
	
	public static String decode(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Base64.Decoder base64Decoder = Base64.getDecoder();
        return new String(base64Decoder.decode(string), "utf-8");
	}
	
}
