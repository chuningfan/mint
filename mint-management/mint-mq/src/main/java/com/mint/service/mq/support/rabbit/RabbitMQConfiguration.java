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
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.CollectionUtils;

import com.mint.common.utils.SpringUtil;
import com.mint.service.mq.annotation.support.RabbitComponent;
import com.mint.service.mq.common.MQRole;

public class RabbitMQConfiguration {
	
	private static final String BINDING_PREFIX = "MQBINDING$";
	
	private static final String LISTENER_ADP_PREFIX = "MQLSTNRADP$";
	
	private static final String LISTENER_CONTAINER_PREFIX = "MQLSTNRCTNR$";
	
	private DefaultListableBeanFactory beanFactory;
	
	private BeanDefinitionRegistry registry;
	
	public RabbitMQConfiguration(BeanDefinitionRegistry registry) throws Exception {
		this.registry = registry;
		this.beanFactory = (DefaultListableBeanFactory) registry;
		createComponents();
	}

	private void createComponents() throws Exception {
		Map<String, Object> beanMap = beanFactory.getBeansWithAnnotation(RabbitComponent.class);
		if (!CollectionUtils.isEmpty(beanMap)) {
			ConnectionFactory connectionFactory = (ConnectionFactory) beanFactory.getBean("rabbitConnectionFactory");
			Set<Entry<String, Object>> entrySet = beanMap.entrySet();
			Iterator<Entry<String, Object>> itr = entrySet.iterator();
			Entry<String, Object> entry = null;
			Object originalObject = null;
			RabbitComponent rc = null;
			String exchangeName = null;
			String queueName = null;
			String routeName = null;
			RabbitStyle style = null;
			Object beanObj = null;
			MQRole role = null;
			while (itr.hasNext()) {
				entry = itr.next();
				originalObject = SpringUtil.getTarget(entry.getValue());
				rc = originalObject.getClass().getAnnotation(RabbitComponent.class);
				exchangeName = rc.exchangeKey();
				queueName = rc.queueKey();
				routeName = rc.routeKey();
				style = rc.style();
				role = rc.role();
				createMQ(originalObject.getClass(), style, exchangeName, routeName, queueName);
				if (MQRole.LISTENER == role) {
					beanObj = entry.getValue();
					createListenerContainer(connectionFactory, beanObj, originalObject.getClass(), 
							rc, createListenerAdapter(beanObj, originalObject.getClass()));
				}
			}
			
		}
	}
	
	private void createListenerContainer(ConnectionFactory connectionFactory, Object bean, Class<?> clazz, RabbitComponent rc, MessageListener listener) {
		SimpleMessageListenerContainer c = new SimpleMessageListenerContainer();
		Queue q = (Queue) beanFactory.getBean(rc.queueKey());
		c.setQueues(q);
		c.setConnectionFactory(connectionFactory);
		c.setMessageListener(listener);
		c.setRabbitAdmin(beanFactory.getBean(RabbitAdmin.class));
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SimpleMessageListenerContainer.class, () -> {
			return c;
		});
		registry.registerBeanDefinition(getListenerContainerBeanName(clazz), builder.getRawBeanDefinition());
	}
	
	private MessageListenerAdapter createListenerAdapter(Object bean, Class<?> clazz) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MessageListenerAdapter.class, () -> {
			return new MessageListenerAdapter(bean);
		});
		registry.registerBeanDefinition(getListenerAdapterBeanName(clazz), builder.getRawBeanDefinition());
		return (MessageListenerAdapter) beanFactory.getBean(getListenerAdapterBeanName(clazz));
	}
	
	private void createMQ(Class<?> clazz, RabbitStyle style, String exchangeName, String routeName, String queueName) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(Queue.class, () -> {
			Queue q = new Queue(queueName, true, false, true);
			return q;
		});
		registry.registerBeanDefinition(queueName, builder.getRawBeanDefinition());
		Queue q = (Queue) beanFactory.getBean(queueName);
		Binding binding = null;
		switch (style) {
		case FANOUT: 
			FanoutExchange fx = getOrCreateExchange(style, exchangeName, FanoutExchange.class);
			binding = BindingBuilder.bind(q).to(fx);
			break;
		case TOPIC: 
			TopicExchange tx = getOrCreateExchange(style, exchangeName, TopicExchange.class);
			binding = BindingBuilder.bind(q).to(tx).with(routeName);
			break;
		default: // direct & TTL
			DirectExchange dx = getOrCreateExchange(style, exchangeName, DirectExchange.class);
			binding = BindingBuilder.bind(q).to(dx).with(routeName);
			break;
		}
		if (binding != null) {
			BeanDefinitionBuilder bindingBuilder = BeanDefinitionBuilder.genericBeanDefinition(Binding.class);
			bindingBuilder.getRawBeanDefinition().setSource(binding);
			registry.registerBeanDefinition(getBindingBeanName(clazz), bindingBuilder.getRawBeanDefinition());
		}
	}
	
	private String getListenerContainerBeanName(Class<?> clazz) {
		return String.format("%s%s", LISTENER_CONTAINER_PREFIX, clazz.getName());
	}
	
	private String getListenerAdapterBeanName(Class<?> clazz) {
		return String.format("%s%s", LISTENER_ADP_PREFIX, clazz.getName());
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
