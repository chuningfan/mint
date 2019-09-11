package com.mint.service.map.dto;

public class MapQueryRequest {
	
	private String[] keyWords;
	
	private Long id;
	
	private String key;
	
	private AgencyInfo info;
	
	private String collectionName;
	
	public String[] getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String[] keyWords) {
		this.keyWords = keyWords;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AgencyInfo getInfo() {
		return info;
	}

	public void setInfo(AgencyInfo info) {
		this.info = info;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
}
