package com.mint.service.user.metadata;


import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetadata;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineProvider;
import com.mint.service.pipeline.pre.AuthValidator;

@Component
public class UserServiceMetadataProvider implements ServiceMetadataProvider {

	@Override
	public ServiceMetadata metaData() {
		ServiceMetadata md = new ServiceMetadata();
		md.setServiceId("mint-user-service");
		return md;
	}

	@Override
	public void initPipeline(PipelineProvider provider) {
		provider.removePre(AuthValidator.ID);
	}

}
