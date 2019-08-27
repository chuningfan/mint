package com.mint.service.user.metadata;


import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetaData;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineProvider;

@Component
public class UserServiceMetadataProvider implements ServiceMetadataProvider {

	@Override
	public ServiceMetaData metaData() {
		ServiceMetaData md = new ServiceMetaData();
		md.setServiceId("user");
		return md;
	}

	@Override
	public void initPipeline(PipelineProvider provider) {
		// TODO Auto-generated method stub
		
	}

}
