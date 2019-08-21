package com.mint.service.cache.annotation.importer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.mint.service.cache.annotation.ListenTo;
import com.mint.service.cache.support.redis.RedisMessageListener;

public class EnableRedisMsgListenerImporter implements ImportBeanDefinitionRegistrar {

	private static final String LISTENER_ADAPTER_SUFFIX = "_ListenerAdapter";
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		DefaultListableBeanFactory bf = (DefaultListableBeanFactory) registry;
		RedisConnectionFactory rcf = null;
		try {
			rcf = bf.getBean(RedisConnectionFactory.class);
			RedisMessageListenerContainer container = new RedisMessageListenerContainer();
			container.setConnectionFactory(rcf);
			@SuppressWarnings("unchecked")
			Class<? extends RedisMessageListener>[] listeners = (Class<? extends RedisMessageListener>[]) importingClassMetadata.getAnnotationAttributes("com.mint.service.common.annotation.EnableRedisMessageListener").get("listeners");
			if (listeners != null && listeners.length > 0) {
				RedisMessageListener rml = null;
				MessageListenerAdapter adapter = null;
				String listenerAdapterBeanName = null;
				ListenTo lt = null;
				for (Class<? extends RedisMessageListener> c: listeners) {
					if (!c.isAnnotationPresent(ListenTo.class)) {
						continue;
					}
					lt = c.getAnnotation(ListenTo.class);
					try {
						rml = bf.getBean(c);
						adapter = new MessageListenerAdapter(rml, "onMessage");
						listenerAdapterBeanName = new StringBuilder(rml.getClass().getSimpleName()).append(LISTENER_ADAPTER_SUFFIX).toString();
						bf.registerSingleton(listenerAdapterBeanName, adapter);
						adapter = (MessageListenerAdapter) bf.getBean(listenerAdapterBeanName);
						container.addMessageListener(adapter, new PatternTopic(lt.value()));
					} catch (BeansException e) {
						continue;
					}
				}
				bf.registerSingleton("redisMessageListenerContainer ", container);
			}
		} catch (BeansException e) {
			return;
		}
	}

}
