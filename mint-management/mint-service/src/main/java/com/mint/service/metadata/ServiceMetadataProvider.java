package com.mint.service.metadata;

import com.mint.service.pipeline.PipelineWorker;

public interface ServiceMetadataProvider {
	
	ServiceMetaData metaData();
	
	void initPipeline(PipelineWorker pipelineWorker);
	
}
