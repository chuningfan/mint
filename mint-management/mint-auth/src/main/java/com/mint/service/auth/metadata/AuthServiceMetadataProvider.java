package com.mint.service.auth.metadata;

import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetaData;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineWorker;

@Component
public class AuthServiceMetadataProvider implements ServiceMetadataProvider {

	@Override
	public ServiceMetaData metaData() {
		ServiceMetaData md = new ServiceMetaData();
		md.setServiceId("auth-service");
		return md;
	}

	@Override
	public void initPipeline(PipelineWorker pipelineWorker) {
		
	}

}