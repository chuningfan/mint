package com.mint.service.order.manager;

import java.math.BigDecimal;

import com.mint.service.order.dto.OrderDto;
import com.mint.service.order.dto.OrderProcessingResult;
import com.mint.service.order.enums.PaymentMethod;

public interface OrderManagerInterface {

	OrderProcessingResult<BigDecimal> calculatePrice(OrderDto order);
	
	void process0(OrderProcessingResult<BigDecimal> calculatedOrder,  PaymentMethod paymentMethod);
	
}
