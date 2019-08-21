package com.mint.service.meta;

import java.util.List;

public class ServiceMetaData {
	
	private String serviceId;
	
	private List<Long> supportedRoleIds;
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public List<Long> getSupportedRoleIds() {
		return supportedRoleIds;
	}

	public void setSupportedRoleIds(List<Long> supportedRoleIds) {
		this.supportedRoleIds = supportedRoleIds;
	}
	
}
