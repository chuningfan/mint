package com.mint.service.database.entity;

public class StandardEntityWithVersion extends StandardEntity {

	private static final long serialVersionUID = -4343379144458742756L;

	private Long previousVersion;
	
	private Long version;

	public Long getPreviousVersion() {
		return previousVersion;
	}

	public void setPreviousVersion(Long previousVersion) {
		this.previousVersion = previousVersion;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
}
