package com.mint.service.order.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.mint.service.order.enums.OrderItemStatus;

public class OrderItemDto {
	
	private Long id;
	
	private Long orderId;
	
	private String orderNumber;
	
	private OrderItemStatus status;
	
	private ProductDto product;
	
	private Set<DiscountDto> discounts;
	
	private BigDecimal amount;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public OrderItemStatus getStatus() {
		return status;
	}

	public void setStatus(OrderItemStatus status) {
		this.status = status;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public Set<DiscountDto> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Set<DiscountDto> discounts) {
		this.discounts = discounts;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
