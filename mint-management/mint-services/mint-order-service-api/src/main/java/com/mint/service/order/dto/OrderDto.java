package com.mint.service.order.dto;

import java.util.Date;
import java.util.Set;

import com.mint.service.order.enums.OrderStatus;

public class OrderDto {
	
	private Long id;
	
	private String order_number;
	
	private Date createdDate;
	
	private OrderStatus status;
	
	private Set<OrderItemDto> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Set<OrderItemDto> getItems() {
		return items;
	}

	public void setItems(Set<OrderItemDto> items) {
		this.items = items;
	}
	
}
