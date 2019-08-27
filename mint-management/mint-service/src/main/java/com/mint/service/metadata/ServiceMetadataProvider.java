package com.mint.service.metadata;

import com.mint.service.pipeline.PipelineProvider;

public interface ServiceMetadataProvider {
	
	ServiceMetaData metaData();
	
	default void initPipeline(PipelineProvider provider) {
		
	}
	
}
