package com.mint.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanHelper implements ApplicationContextAware {

	private static ApplicationContext cxt;

	private static ListableBeanFactory listableBeanFactory;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanHelper.cxt = applicationContext;
		listableBeanFactory = cxt;
	}	
	
	public static <T> T getBean(Class<T> type) {
		return cxt.getBean(type);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) cxt.getBean(beanName);
	}

	public static ApplicationContext getCxt() {
		return cxt;
	}

	public static ListableBeanFactory getListableBeanFactory() {
		return listableBeanFactory;
	}
	
}
