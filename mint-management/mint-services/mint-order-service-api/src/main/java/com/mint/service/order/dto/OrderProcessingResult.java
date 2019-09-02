package com.mint.service.order.dto;

import java.util.Set;

import com.mint.common.exception.MintException;

public class OrderProcessingResult<T> {
	
	private Long orderId;
	
	private Long requestId;
	
	private String message;
	
	private Set<MintException> eSet;
	
	private T data;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<MintException> geteSet() {
		return eSet;
	}

	public void seteSet(Set<MintException> eSet) {
		this.eSet = eSet;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
