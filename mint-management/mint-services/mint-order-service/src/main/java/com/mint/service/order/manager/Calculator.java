package com.mint.service.order.manager;

import java.math.BigDecimal;
import java.util.Set;

import javax.transaction.Transactional;

import com.google.common.collect.Sets;
import com.mint.common.exception.MintException;
import com.mint.service.order.dto.DiscountDto;
import com.mint.service.order.dto.OrderDto;
import com.mint.service.order.dto.OrderItemDto;
import com.mint.service.order.dto.OrderProcessingResult;
import com.mint.service.order.dto.ProductDto;

public abstract class Calculator {

	@Transactional(dontRollbackOn = MintException.class)
	protected OrderProcessingResult<BigDecimal> cal(OrderDto order) {
		validate(order);
		Set<MintException> eSet = Sets.newHashSet();
		BigDecimal result = new BigDecimal(0);
		for (OrderItemDto item: order.getItems()) {
			try {
				validate(item);
				lockResource(item.getProduct());
				result.add(calOrderItems(item.getAmount(), item.getProduct(), item.getDiscounts()));
			} catch (MintException e) {
				eSet.add(e);
				continue;
			}
		}
		OrderProcessingResult<BigDecimal> res = new OrderProcessingResult<BigDecimal>();
		res.setData(result);
		res.seteSet(eSet);
		res.setOrderId(order.getId());
		res.setRequestId(order.getRequestId());
		return res;
	}
	
	protected abstract void lockResource(ProductDto prod) throws MintException;
	
	protected abstract BigDecimal calOrderItems(BigDecimal amount, ProductDto prod, Set<DiscountDto> discounts);
	
	protected void validate(OrderDto order) throws MintException {
		
	}
	
	protected void validate(OrderItemDto item) throws MintException {
		
	}
	
	
}
