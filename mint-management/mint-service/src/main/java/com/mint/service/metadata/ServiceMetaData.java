package com.mint.service.metadata;

import java.util.List;

public class ServiceMetaData {
	
	private String serviceId;
	
	private List<Long> supportedRoleIds;
	
	private String serviceIp;
	
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

	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}
	
}
