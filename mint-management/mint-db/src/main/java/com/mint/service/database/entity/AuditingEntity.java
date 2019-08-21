package com.mint.service.database.entity;

import java.io.Serializable;
import java.util.Date;

public class AuditingEntity implements Serializable {
	
	private static final long serialVersionUID = -5456350593218527744L;

	private Long createdBy;
	
	private Date createdDate;
	
	private Long modifiedBy;
	
	private Date modifiedDate;

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
