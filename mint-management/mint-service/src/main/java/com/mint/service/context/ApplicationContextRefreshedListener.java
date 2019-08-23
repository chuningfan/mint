package com.mint.service.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.mint.service.metadata.ServiceMetaData;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineWorker;

@Component
public class ApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext cxt = event.getApplicationContext();
		ServiceMetadataProvider provider = cxt.getBean(ServiceContext.metadataProvider);
		provider.initPipeline(cxt.getBean(PipelineWorker.class)); // 回调方法，初始化时根据service所需 添加或删除pipeline members
		ServiceMetaData metaData =  provider.metaData();
		ServiceContext.id = metaData.getServiceId();
		ServiceContext.supportedRoleIds = metaData.getSupportedRoleIds();
	}

}
