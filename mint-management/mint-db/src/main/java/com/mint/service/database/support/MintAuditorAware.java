package com.mint.service.database.support;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.mint.common.context.UserContext;
import com.mint.common.context.UserContextThreadLocal;

@Component("mintAuditorAware")
public class MintAuditorAware implements AuditorAware<Long>{
	
	@Override
	public Optional<Long> getCurrentAuditor() {
		UserContext context = UserContextThreadLocal.get();
		if (context == null) {
			return Optional.of(-1L);
		}
		return Optional.ofNullable(context.getAccountId());
	}
	
}
