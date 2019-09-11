package com.mint.service.map.dto;

import java.util.Set;

public class AgencyInfo {
	
	private Long agencyId;
	
	private double longitude; // 经度
	
	private double latitude; // 纬度
	
	private String address;
	
	private Set<String> keys;
	
	private double serveRadius;

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<String> getKeys() {
		return keys;
	}

	public void setKeys(Set<String> keys) {
		this.keys = keys;
	}

	public double getServeRadius() {
		return serveRadius;
	}

	public void setServeRadius(double serveRadius) {
		this.serveRadius = serveRadius;
	}
	
}
