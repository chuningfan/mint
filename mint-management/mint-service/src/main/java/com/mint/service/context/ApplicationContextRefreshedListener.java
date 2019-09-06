package com.mint.service.context;

import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetadata;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineProvider;
import com.mint.service.pipeline.pre.AuthValidator;
import com.mint.service.pipeline.pre.RateLimitationValidator;

@Component
public class ApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext cxt = event.getApplicationContext();
		ServiceMetadataProvider metadataProvider = cxt.getBean(ServiceContext.metadataProvider);
		PipelineProvider pipelineProvider = cxt.getBean(PipelineProvider.class);
		// 加入默认pipeline member
		pipelineProvider.setPre(new RateLimitationValidator(3000).withRetry(2).retryWithInterval(3 * 1000, TimeUnit.MILLISECONDS), 0);
		pipelineProvider.setPre(new AuthValidator(), 1);
		metadataProvider.initPipeline(pipelineProvider); // 回调方法，初始化时根据service所需 添加或删除pipeline members
		ServiceMetadata metaData =  metadataProvider.metaData();
		ServiceContext.id = metaData.getServiceId();
		ServiceContext.supportedRoleIds = metaData.getSupportedRoleIds();
	}

}
