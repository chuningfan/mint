package com.mint.service.gateway.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.gateway.filter.BlackListValidation;
import com.mint.service.metadata.ServiceMetaData;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineProvider;
import com.mint.service.security.guard.BlackListConcierge;

@Configuration
public class GatewayServiceMetadataProvider implements ServiceMetadataProvider {

	@Autowired
	private RedisHelper redisHelper;
	
	@Bean
	public BlackListConcierge blackListConcierge() {
		return new BlackListConcierge(redisHelper);
	}
	
	@Bean
	public BlackListValidation blackListValidation(BlackListConcierge blackListConcierge) {
		return new BlackListValidation(blackListConcierge);
	}
	
	@Override
	public ServiceMetaData metaData() {
		ServiceMetaData md = new ServiceMetaData();
		md.setServiceId("gateway-service");
		return md;
	}

	@Override
	public void initPipeline(PipelineProvider pipelineProvider) {
		pipelineProvider.setPre(blackListValidation(blackListConcierge()), 0);
		pipelineProvider.removePre("auth-validator");
	}

}
