package com.mint.common.enums;
public enum ProtocolType {
	
	HTTP("http://"), HTTPS("https://");
	
	private String protocol;

	private ProtocolType(String protocol) {
		this.protocol = protocol;
	}

	public String getProtocol() {
		return protocol;
	}
	
}