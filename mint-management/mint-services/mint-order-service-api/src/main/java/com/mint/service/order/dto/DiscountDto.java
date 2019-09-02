package com.mint.service.order.dto;

import com.mint.service.order.enums.DiscountType;

public class DiscountDto {
	
	private Long id;
	
	private DiscountType type;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DiscountType getType() {
		return type;
	}

	public void setType(DiscountType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
