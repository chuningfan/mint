package com.mint.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.google.common.collect.Lists;
import com.mint.common.exception.SPILoadingException;

public class CommonServiceLoader {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonServiceLoader.class);
	
	public static <T> T getSingleService(Class<T> clazz, DefaultListableBeanFactory beanFactory) {
		 ServiceLoader<T> loader = ServiceLoader.load(clazz);
		 Iterator<T> itr = loader.iterator();
		 T t = null;
		 while (itr.hasNext()) {
			 if (t != null) {
				 throw new SPILoadingException("There are too many implementation for target interface");
			 }
			 t = itr.next();
		 }
		 if (beanFactory != null) {
			 try {
				 t = beanFactory.getBean(clazz);
			 } catch (BeansException e) {
				 LOG.info("No SPI implementation for {} is as a spring bean.", clazz.getName());
			 }
		 }
		 return t;
	}
	
	public static <T> Collection<T> getMultipleServices(Class<T> clazz, DefaultListableBeanFactory beanFactory) {
		 ServiceLoader<T> loader = ServiceLoader.load(clazz);
		 Iterator<T> itr = loader.iterator();
		 T t = null;
		 Collection<T> coll = Lists.newArrayList();
		 while (itr.hasNext()) {
			 t = itr.next();
			 if (beanFactory != null) {
				 try {
					 t = beanFactory.getBean(clazz);
				 } catch (BeansException e) {
					 LOG.info("No SPI implementation for {} is as a spring bean, skip.", clazz.getName());
				 }
			 }
			 coll.add(t);
		 }
		 return coll;
	}
	
}
