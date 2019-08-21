package com.mint.service.test.meta;

import org.springframework.stereotype.Component;

import com.mint.service.meta.ServiceMetaData;
import com.mint.service.meta.ServiceMetaDataProvider;
import com.mint.service.pipeline.PipelineWorker;

@Component
public class TesterServiceMetadataProvider implements ServiceMetaDataProvider {

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
