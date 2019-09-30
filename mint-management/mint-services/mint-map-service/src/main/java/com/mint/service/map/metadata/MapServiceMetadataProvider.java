package com.mint.service.map.metadata;

import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetadata;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineProvider;
import com.mint.service.pipeline.pre.AuthValidator;

@Component
public class MapServiceMetadataProvider implements ServiceMetadataProvider {

	@Override
	public ServiceMetadata metaData() {
		ServiceMetadata sm = new ServiceMetadata();
		sm.setServiceId("map-service");
		return sm;
	}

	@Override
	public void initPipeline(PipelineProvider provider) {
		provider.removePre(AuthValidator.ID);
	}

	
}
