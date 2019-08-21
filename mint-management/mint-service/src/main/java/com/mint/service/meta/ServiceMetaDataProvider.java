package com.mint.service.meta;

import com.mint.service.pipeline.PipelineWorker;

public interface ServiceMetaDataProvider {
	
	ServiceMetaData metaData();
	
	void initPipeline(PipelineWorker pipelineWorker);
	
}
