package com.mint.service.database.entity;

public class SerialEntity extends AuditingEntity {

	private static final long serialVersionUID = 4291964186557917109L;

	private Long version;
	
	private Long previousVersion;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getPreviousVersion() {
		return previousVersion;
	}

	public void setPreviousVersion(Long previousVersion) {
		this.previousVersion = previousVersion;
	}
	
}
