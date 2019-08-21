package com.mint.service.annotation.importer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.mint.service.context.ServiceContext;
import com.mint.service.interceptor.MintInterceptor;
import com.mint.service.meta.ServiceMetaData;
import com.mint.service.meta.ServiceMetaDataProvider;
import com.mint.service.pipeline.PipelineWorker;

public class MintServiceImporter implements ImportBeanDefinitionRegistrar {

	private static final Logger LOG = LoggerFactory.getLogger(MintServiceImporter.class);
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		// 获取service metadata provider class
		Map<String, Object> attrMap = importingClassMetadata.getAnnotationAttributes("com.mint.service.annotation.MintService");
		@SuppressWarnings("unchecked")
		Class<? extends ServiceMetaDataProvider> clazz = (Class<? extends ServiceMetaDataProvider>) attrMap.get("metaDataProvider");
		
		int readTimeout = (int) attrMap.get("readTimeout");
		int connectTimeout = (int) attrMap.get("connectTimeout");
		int longConnectionReadTimeout = (int) attrMap.get("longConnectionReadTimeout");
		boolean validateContext = (boolean) attrMap.get("validateContext");
		@SuppressWarnings("unchecked")
		Class<? extends MintInterceptor>[] includeInterceptors = (Class<? extends MintInterceptor>[]) attrMap.get("includeInterceptors");
		@SuppressWarnings("unchecked")
		Class<? extends MintInterceptor>[] excludeInterceptors = (Class<? extends MintInterceptor>[]) attrMap.get("excludeInterceptors");
		// 注册默认spring bean
		registerBean(registry, PipelineWorker.class, "pipelineWorker");
		// 获取service metadata
		DefaultListableBeanFactory bf = (DefaultListableBeanFactory) registry;
		ServiceMetaDataProvider provider = bf.getBean(clazz);
		provider.initPipeline(bf.getBean(PipelineWorker.class)); // 回调方法，初始化时根据service所需 添加或删除pipeline members
		ServiceMetaData metaData =  provider.metaData();
		// 赋值ServiceContext
		ServiceContext.beanFactory = bf;
		ServiceContext.id = metaData.getServiceId();
		ServiceContext.supportedRoleIds = metaData.getSupportedRoleIds();
		ServiceContext.readTimeout = readTimeout <= 0 ? 30000 : readTimeout;
		ServiceContext.connectTimeout = connectTimeout <= 0 ? 10000 : connectTimeout;
		ServiceContext.longConnectionReadTimeout = longConnectionReadTimeout <= 0 ? 180000 : longConnectionReadTimeout;
		ServiceContext.validateContext = validateContext;
		if (ArrayUtils.isNotEmpty(excludeInterceptors) && ArrayUtils.isNotEmpty(includeInterceptors)) {
			ServiceContext.interceptors = Optional.ofNullable(includeInterceptors).filter(i -> !ArrayUtils.contains(excludeInterceptors, i)).get();
		} else if (ArrayUtils.isEmpty(excludeInterceptors) && ArrayUtils.isNotEmpty(includeInterceptors)) {
			ServiceContext.interceptors = includeInterceptors;
		} else {
			LOG.info("No interceptors found.");
		}
		try {
			ServiceContext.serverAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			LOG.error(e.getMessage());
		}
	}

	private void registerBean(BeanDefinitionRegistry registry, Class<?> beanClass, String beanName, Object...counstructionArgs) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
		if (counstructionArgs != null) {
			for (Object arg: counstructionArgs) {
				builder.addConstructorArgValue(arg);
			}
		}
		builder.setScope(BeanDefinition.SCOPE_SINGLETON);
		registry.registerBeanDefinition(beanName, builder.getRawBeanDefinition());
	}
	
}
