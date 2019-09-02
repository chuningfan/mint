package com.mint.service.order.dto;

import com.mint.service.order.enums.Action;

public class OrderRequest {
	
	private Long requestId;
	
	private OrderDto order;
	
	private Action action;

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public OrderDto getOrder() {
		return order;
	}

	public void setOrder(OrderDto order) {
		this.order = order;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
}
