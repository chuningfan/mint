package com.mint.service.database.entity;

import java.io.Serializable;
import java.util.Date;

public class StandardEntity implements Serializable {

	private static final long serialVersionUID = 3363050223471188979L;
	
	private Long id;
	
	private Long OperaterId;
	
	private Long createrId;
	
	private Long modifierId;
	
	private Date createdDate;
	
	private Date modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOperaterId() {
		return OperaterId;
	}

	public void setOperaterId(Long operaterId) {
		OperaterId = operaterId;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
