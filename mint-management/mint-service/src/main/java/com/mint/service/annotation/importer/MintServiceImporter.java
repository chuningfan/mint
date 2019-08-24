package com.mint.service.annotation.importer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.mint.service.interceptor.InterceptorConfiguration;
import com.mint.service.interceptor.MintInterceptor;
import com.mint.service.metadata.ServiceMetadataProvider;
import com.mint.service.pipeline.PipelineWorker;

public class MintServiceImporter implements ImportBeanDefinitionRegistrar {

	private static final Logger LOG = LoggerFactory.getLogger(MintServiceImporter.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		// 获取service metadata provider class
		Map<String, Object> attrMap = importingClassMetadata.getAnnotationAttributes("com.mint.service.annotation.MintService");
		Class<? extends ServiceMetadataProvider> clazz = (Class<? extends ServiceMetadataProvider>) attrMap.get("metadataProvider");
		
		int readTimeout = (int) attrMap.get("readTimeout");
		int connectTimeout = (int) attrMap.get("connectTimeout");
		int longConnectionReadTimeout = (int) attrMap.get("longConnectionReadTimeout");
		Class<? extends MintInterceptor>[] includeInterceptors = attrMap.get("includeInterceptors") == null ? null : (Class<? extends MintInterceptor>[]) attrMap.get("includeInterceptors");
		Class<? extends MintInterceptor>[] excludeInterceptors = attrMap.get("excludeInterceptors") == null ? null : (Class<? extends MintInterceptor>[]) attrMap.get("excludeInterceptors");
		String[] contextInterceptorExcludePaths = attrMap.get("contextInterceptorExcludePaths") == null ? null : (String[])attrMap.get("contextInterceptorExcludePaths");
		// 注册默认spring bean
		registerBean(registry, PipelineWorker.class, "pipelineWorker");
		// 获取service metadata
		DefaultListableBeanFactory bf = (DefaultListableBeanFactory) registry;
		// 赋值ServiceContext
		ServiceContext.metadataProvider = clazz;
		ServiceContext.beanFactory = bf;
		ServiceContext.readTimeout = readTimeout <= 0 ? 30000 : readTimeout;
		ServiceContext.connectTimeout = connectTimeout <= 0 ? 10000 : connectTimeout;
		ServiceContext.longConnectionReadTimeout = longConnectionReadTimeout <= 0 ? 180000 : longConnectionReadTimeout;
		if (ArrayUtils.isNotEmpty(excludeInterceptors) && ArrayUtils.isNotEmpty(includeInterceptors)) {
			List<Class<? extends MintInterceptor>> incList = Stream.of(includeInterceptors).collect(Collectors.toList());
			Iterator<Class<? extends MintInterceptor>> itr = incList.iterator();
			List<Class<? extends MintInterceptor>> excList = Stream.of(excludeInterceptors).collect(Collectors.toList());
			while (itr.hasNext()) {
				if (excList.contains(itr.next())) {
					itr.remove();
				}
			}
			ServiceContext.interceptors = incList.toArray(new Class[]{});
		} else if (ArrayUtils.isEmpty(excludeInterceptors) && ArrayUtils.isNotEmpty(includeInterceptors)) {
			ServiceContext.interceptors = includeInterceptors;
		} else {
			LOG.info("No interceptors found.");
		}
		if (ArrayUtils.isNotEmpty(contextInterceptorExcludePaths)) {
			ServiceContext.contextInterceptorExcludePaths = contextInterceptorExcludePaths;
		}
		try {
			ServiceContext.serverAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			LOG.error(e.getMessage());
		}
		registerBean(registry, InterceptorConfiguration.class, "interceptorConfiguration");
		
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
