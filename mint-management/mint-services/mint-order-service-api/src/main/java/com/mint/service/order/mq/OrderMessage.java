package com.mint.service.order.mq;

import java.util.Date;

import com.mint.service.order.dto.OrderProcessingResult;

public class OrderMessage<T> {
	
	private Long orderRequestId;
	
	private Long orderId;
	
	private OrderProcessingResult<T> result;
	
	private Date time;

	public Long getOrderRequestId() {
		return orderRequestId;
	}

	public void setOrderRequestId(Long orderRequestId) {
		this.orderRequestId = orderRequestId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrderProcessingResult<T> getResult() {
		return result;
	}

	public void setResult(OrderProcessingResult<T> result) {
		this.result = result;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}
