package com.mint.service.gateway.metadata;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.context.annotation.Configuration;

import com.mint.service.gateway.filter.BlackListValidation;
import com.mint.service.metadata.ServiceMetaData;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineWorker;
import com.mint.service.pipeline.defaultImpl.AuthValidation;
import com.mint.service.pipeline.defaultImpl.RateLimitation;

@Configuration
public class GatewayServiceMetadataProvider implements ServiceMetadataProvider {

	public static final List<String> UNCHECKED_URI = Lists.newArrayList("/userReg", "/userLogin", "/mint-test/service/exc");
	
	@Override
	public ServiceMetaData metaData() {
		ServiceMetaData md = new ServiceMetaData();
		md.setServiceId("gateway-service");
		return md;
	}

	@Override
	public void initPipeline(PipelineWorker pipelineWorker) {
		pipelineWorker.removeMember(AuthValidation.ID);
		pipelineWorker.appendMember(new BlackListValidation(), 0, true);
		pipelineWorker.appendMember(new RateLimitation(3000), 1, true);
	}

}
