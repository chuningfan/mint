package com.mint.service.mq.annotation.importer;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.service.mq.common.SupportedMQ;
import com.mint.service.mq.support.rabbit.RabbitMQConfiguration;

public class MintMQImporter implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> attrMap = importingClassMetadata.getAnnotationAttributes("com.mint.service.mq.annotation.EnableMintMQ");
		SupportedMQ smq = (SupportedMQ) attrMap.get("use");
		switch (smq) {
		case ACTIVE:
			throw MintException.getException(Error.UNSUPPORTED_ERROR, null, null).setMsg("Active MQ has no implements.");
		case KAFKA:
			throw MintException.getException(Error.UNSUPPORTED_ERROR, null, null).setMsg("Kafka has no implements.");
		default: // rabbit
			initRabbitMQComponents(registry);
			break;
		}
	}

	private void initRabbitMQComponents(BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RabbitMQConfiguration.class);
		builder.addConstructorArgValue(registry);
		registry.registerBeanDefinition("rabbitMQConfiguration", builder.getRawBeanDefinition());
	}
	
}
