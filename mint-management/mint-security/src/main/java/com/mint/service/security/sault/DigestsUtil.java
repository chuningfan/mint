package com.mint.service.security.sault;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class DigestsUtil {
	private static final char[] DIGITS=
	        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	public static String shap(String str)   {
        MessageDigest sha = null;
        byte[] shaBytes = null;
        try {
		  sha = MessageDigest.getInstance("SHA1");
		  byte[] byteArray = str.getBytes("UTF-8");
		   shaBytes = sha.digest(byteArray);
	    } catch (Exception e) {
		  e.printStackTrace();
	   }
		return toHex(shaBytes, DIGITS);
	}
	
	private static  String toHex(byte[] data, char[] digits) {
		int length = data.length;
        char[] out = new char[length << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < length; i++) {
            out[j++] = digits[(0xF0 & data[i]) >>> 4];
            out[j++] = digits[0x0F & data[i]];
        }
        return new String(out);
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(DigestsUtil.shap("admin"));
	}

}
