package com.mint.service.map.dto;

import java.util.Set;

public class AgencyInfo {
	
	private Long agencyId;
	
	private double longitude; // 经度
	
	private double latitude; // 纬度
	
	private Set<String> keys;
	
	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Set<String> getKeys() {
		return keys;
	}

	public void setKeys(Set<String> keys) {
		this.keys = keys;
	}
	
}
