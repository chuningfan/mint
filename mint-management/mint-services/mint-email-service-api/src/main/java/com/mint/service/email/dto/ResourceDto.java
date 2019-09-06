package com.mint.service.email.dto;

import java.io.Serializable;

public class ResourceDto {
	
	private Serializable resourceId;
	
	private String resourcePath;

	public Serializable getResourceId() {
		return resourceId;
	}

	public void setResourceId(Serializable resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
}
