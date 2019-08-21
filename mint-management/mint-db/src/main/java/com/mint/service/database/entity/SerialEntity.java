package com.mint.service.database.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class SerialEntity extends AuditingEntity implements Serializable {

	private static final long serialVersionUID = 4291964186557917109L;

	@Column(name="version_number")
	private Long versionNumber = 0L;
	
	@Column(name="previous_version_number")
	private Long previousVersionNumber;

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Long getPreviousVersionNumber() {
		return previousVersionNumber;
	}

	public void setPreviousVersionNumber(Long previousVersionNumber) {
		this.previousVersionNumber = previousVersionNumber;
	}

}
