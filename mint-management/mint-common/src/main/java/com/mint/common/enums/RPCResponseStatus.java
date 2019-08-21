package com.mint.common.enums;

public enum RPCResponseStatus {
	
	SUCCESSFUL(0), FAILED(1), TIMEOUT(-1);
	
	private int code;
	
	private RPCResponseStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
}
