package com.mint.service.metadata;

import com.mint.service.pipeline.PipelineProvider;

public interface ServiceMetadataProvider {
	
	ServiceMetadata metaData();
	
	default void initPipeline(PipelineProvider provider) {
		
	}
	
}
