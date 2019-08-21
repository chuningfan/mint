package com.mint.service.database.entity;

public class VersionEntity {
	
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
