package com.mint.service.test.meta;

import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetaData;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineWorker;

@Component
public class TesterServiceMetadataProvider implements ServiceMetadataProvider {

	@Override
	public ServiceMetaData metaData() {
		ServiceMetaData md = new ServiceMetaData();
		md.setServiceId("test");
		return md;
	}

	@Override
	public void initPipeline(PipelineWorker pipelineWorker) {
		
	}

}
