package com.mint.service.order.manager.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.order.dto.DiscountDto;
import com.mint.service.order.dto.OrderDto;
import com.mint.service.order.dto.OrderProcessingResult;
import com.mint.service.order.dto.ProductDto;
import com.mint.service.order.enums.DiscountType;
import com.mint.service.order.enums.PaymentMethod;
import com.mint.service.order.manager.Calculator;
import com.mint.service.order.manager.OrderManagerInterface;

@Component
public class DefaultOrderManager extends Calculator implements OrderManagerInterface {

	private static final String GLOBAL_LOCK_PREFIX = "GL$";
	
	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public OrderProcessingResult<BigDecimal> calculatePrice(OrderDto order) throws MintException {
		return cal(order);
	}

	@Override
	public void process0(OrderProcessingResult<BigDecimal> calculatedOrder, PaymentMethod paymentMethod) {
		BigDecimal needPay = calculatedOrder.getData();
		Long requestId = calculatedOrder.getRequestId();
		Long orderId = calculatedOrder.getOrderId();
		switch (paymentMethod) {
		case ALIPAY: break;
		default: // wechat 
			break;
		}
	}

	@Override
	protected void lockResource(ProductDto prod) throws MintException {
		String productIdStr = prod.getId().toString();
		if (!doGlobalLockForProduct(productIdStr)) {
			int retryTimes = 0; 
			for (;;) { // spin
				if (++retryTimes > 2 || doGlobalLockForProduct(productIdStr)) {
					break;
				}
			}
			if (retryTimes > 2) {
				throw MintException.getException(Error.RESOURCE_NOT_AVAILABLE_ERROR, null, null);
			}
		}
	}

	private boolean doGlobalLockForProduct(String productIdStr) {
		return redisHelper.globalLock(GLOBAL_LOCK_PREFIX + productIdStr, "lock", 3 * 1000L, TimeUnit.MILLISECONDS);
	}
	
	@Override
	protected BigDecimal calOrderItems(BigDecimal amount, ProductDto prod, Set<DiscountDto> discounts) {
		BigDecimal totalPrice = prod.getPrice().multiply(amount);
		totalPrice.setScale(2, RoundingMode.HALF_DOWN);
		if (CollectionUtils.isEmpty(discounts)) {
			return totalPrice;
		}
		BigDecimal calculated = new BigDecimal(0);
		double p = 0;
		double a = 0;
		for (DiscountDto d: discounts) {
			if (DiscountType.AMOUNT == d.getType()) {
				a += d.getValue();
			} else if (DiscountType.PERCENTAGE == d.getType()) {
				p += d.getValue();
			} else {
				// invalid discount
				continue;
			}
		}
		calculated = totalPrice.min((totalPrice.multiply(BigDecimal.valueOf(p))));
		calculated = calculated.min(BigDecimal.valueOf(a));
		return calculated;
	}


	
}
