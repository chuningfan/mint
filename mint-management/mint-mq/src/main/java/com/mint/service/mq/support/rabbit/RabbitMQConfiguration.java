package com.mint.service.mq.support.rabbit;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.CollectionUtils;

import com.mint.common.utils.SpringUtil;
import com.mint.service.mq.annotation.support.RabbitReceiver;

public class RabbitMQConfiguration {
	
	private static final String BINDING_PREFIX = "MQBINDING$";
	
	private DefaultListableBeanFactory beanFactory;
	
	private BeanDefinitionRegistry registry;
	
	public RabbitMQConfiguration(BeanDefinitionRegistry registry) throws Exception {
		this.registry = registry;
		this.beanFactory = (DefaultListableBeanFactory) registry;
		createComponents();
	}

	private void createComponents() throws Exception {
		Map<String, Object> beanMap = beanFactory.getBeansWithAnnotation(RabbitReceiver.class);
		if (!CollectionUtils.isEmpty(beanMap)) {
			Set<Entry<String, Object>> entrySet = beanMap.entrySet();
			Iterator<Entry<String, Object>> itr = entrySet.iterator();
			Entry<String, Object> entry = null;
			Object originalObject = null;
			RabbitReceiver rr = null;
			String exchangeName = null;
			String queueName = null;
			String routeName = null;
			RabbitStyle style = null;
			while (itr.hasNext()) {
				entry = itr.next();
				originalObject = SpringUtil.getTarget(entry.getValue());
				rr = originalObject.getClass().getAnnotation(RabbitReceiver.class);
				exchangeName = rr.exchangeKey();
				queueName = rr.queueKey();
				routeName = rr.routeKey();
				style = rr.style();
				createMQ(originalObject.getClass(), style, exchangeName, routeName, queueName);
			}
		}
	}
	
	private void createMQ(Class<?> clazz, RabbitStyle style, String exchangeName, String routeName, String queueName) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Queue.class);
		builder.addConstructorArgValue(queueName);
		registry.registerBeanDefinition(queueName, builder.getRawBeanDefinition());
		Queue q = (Queue) beanFactory.getBean(queueName);
		Binding binding = null;
		switch (style) {
		case FANOUT: 
			FanoutExchange fx = getOrCreateExchange(style, exchangeName, clazz);
			binding = BindingBuilder.bind(q).to(fx);
			break;
		case TOPIC: 
			TopicExchange tx = getOrCreateExchange(style, exchangeName, clazz);
			binding = BindingBuilder.bind(q).to(tx).with(routeName);
			break;
		default: // direct & TTL
			DirectExchange dx = getOrCreateExchange(style, exchangeName, clazz);
			binding = BindingBuilder.bind(q).to(dx).with(routeName);
			break;
		}
		if (binding != null) {
			BeanDefinitionBuilder bindingBuilder = BeanDefinitionBuilder.genericBeanDefinition(Binding.class);
			bindingBuilder.getRawBeanDefinition().setSource(binding);
			registry.registerBeanDefinition(getBindingBeanName(clazz), bindingBuilder.getRawBeanDefinition());
		}
	}
	
	private String getBindingBeanName(Class<?> clazz) {
		return String.format("%s%s", BINDING_PREFIX, clazz.getName());
	}
	
	private String getExchangeBeanName(RabbitStyle style, Class<?> clazz) {
		return String.format("%s%s", style.getPrefix(), clazz.getName());
	}
	
	@SuppressWarnings("unchecked")
	private <T extends AbstractExchange> T getOrCreateExchange(RabbitStyle style, String exchangeName, Class<?> clazz) {
		AbstractExchange x = null;
		String exchangeBeanName = getExchangeBeanName(style, clazz);
		if(!beanFactory.containsBean(exchangeBeanName)) {
			BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
			builder.addConstructorArgValue(exchangeName);
			registry.registerBeanDefinition(exchangeBeanName, builder.getRawBeanDefinition());
		}
		x = (AbstractExchange) beanFactory.getBean(exchangeBeanName);
		return (T) x;
	}
	
}
