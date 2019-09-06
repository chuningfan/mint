package com.mint.service.email.metadata;

import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetadata;
import com.mint.service.metadata.ServiceMetadataProvider;

@Component
public class EmailServiceMetadataProvider implements ServiceMetadataProvider {

	@Override
	public ServiceMetadata metaData() {
		ServiceMetadata sm = new ServiceMetadata();
		sm.setServiceId("mint-email-service");
		return sm;
	}

}
