package com.mint.service.order.metadata;

import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mint.common.utils.SystemUtil;
import com.mint.service.metadata.ServiceMetadata;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineProvider;
import com.mint.service.pipeline.pre.AuthValidator;

@Component
public class OrderServiceMetadataProvider implements ServiceMetadataProvider {

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceMetadataProvider.class);
	
	@Value("${spring.application.name}")
	private String serviceName;
	
	@Override
	public ServiceMetadata metaData() {
		ServiceMetadata md = new ServiceMetadata();
		md.setServiceId(serviceName);
		try {
			md.setServiceIp(SystemUtil.getLocalAddress());
		} catch (UnknownHostException e) {
			LOG.warn("Cannot get IP address for {}", serviceName);
		}
		return md;
	}

	@Override
	public void initPipeline(PipelineProvider provider) {
		provider.removePre(AuthValidator.ID);
	}
	
}
