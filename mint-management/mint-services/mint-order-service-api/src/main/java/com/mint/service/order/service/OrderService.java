package com.mint.service.order.service;

import com.mint.service.order.dto.OrderDto;

public interface OrderService {
	
	Long processOrder(OrderDto order);
	
	OrderDto getOrderDtoByOrderId(Long orderId);
	
	
}
