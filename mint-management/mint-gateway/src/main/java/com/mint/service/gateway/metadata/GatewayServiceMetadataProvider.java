package com.mint.service.gateway.metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.mint.service.gateway.filter.BlackListValidation;
import com.mint.service.metadata.ServiceMetaData;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineWorker;
import com.mint.service.pipeline.defaultImpl.AuthValidation;
import com.mint.service.pipeline.defaultImpl.RateLimitation;

@Configuration
public class GatewayServiceMetadataProvider implements ServiceMetadataProvider {

	@Autowired
	private BlackListValidation blackListValidation;
	
	@Override
	public ServiceMetaData metaData() {
		ServiceMetaData md = new ServiceMetaData();
		md.setServiceId("gateway-service");
		return md;
	}

	@Override
	public void initPipeline(PipelineWorker pipelineWorker) {
		pipelineWorker.removeMember(AuthValidation.ID);
		pipelineWorker.appendMember(blackListValidation, 0, true);
		pipelineWorker.appendMember(new RateLimitation(3000), 1, true);
	}

}
