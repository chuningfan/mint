package com.mint.service.database.support;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mint.common.context.TokenHandler;
import com.mint.common.context.TokenThreadLocal;

@Component("mintAuditorAware")
public class MintAuditorAware implements AuditorAware<Long>{
	
	@Autowired
	private TokenHandler tokenHandler;
	
	@Override
	public Optional<Long> getCurrentAuditor() {
		String token = TokenThreadLocal.get();
		if (StringUtils.isEmpty(token)) {
			return Optional.of(-1L);
		}
		return Optional.ofNullable(tokenHandler.parse(token).getAccountId());
	}
	
}
