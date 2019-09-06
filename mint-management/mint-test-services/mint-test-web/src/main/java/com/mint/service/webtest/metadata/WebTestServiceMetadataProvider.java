package com.mint.service.webtest.metadata;

import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetadata;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineProvider;

@Component
public class WebTestServiceMetadataProvider implements ServiceMetadataProvider {

	@Override
	public ServiceMetadata metaData() {
		ServiceMetadata md = new ServiceMetadata();
		md.setServiceId("webt-test");
		return md;
	}

	@Override
	public void initPipeline(PipelineProvider provider) {
		// TODO Auto-generated method stub
		
	}

}
