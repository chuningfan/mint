package com.mint.common.enums;

public enum LoginType {
	
	NORMAL(0), WECAHT(1), PHONE(2);
	
	private int code;

	private LoginType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	
}
