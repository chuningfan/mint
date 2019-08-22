package com.mint.common.enums;

public enum LoginResult {
	
	INVALID(0), LOCKED(-1), INACTIVE(-2), NOACCOUNT(-3);
	
	private int code;
	
	private LoginResult(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
