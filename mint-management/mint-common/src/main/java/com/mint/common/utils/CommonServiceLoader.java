package com.mint.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.google.common.collect.Maps;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;

public class CommonServiceLoader {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonServiceLoader.class);
	
	public static <T> T getSingleService(Class<T> clazz, 
			DefaultListableBeanFactory beanFactory) throws MintException {
		 ServiceLoader<T> loader = ServiceLoader.load(clazz);
		 Iterator<T> itr = loader.iterator();
		 T t = null;
		 if (beanFactory != null) {
			 try {
				 t = beanFactory.getBean(clazz);
				 return t;
			 } catch (BeansException e) {
				 LOG.info("No implementation for {} in SPRING container, skip!", clazz.getName());
			 }
		 }
		 while (itr.hasNext()) {
			 if (t != null) {
				 throw MintException.getException(Error.UNEXPECT_DATA_ERROR, null, null);
			 }
			 t = itr.next();
		 }
		 if (t == null) {
			 throw MintException.getException(Error.IMPLEMENTATION_NOT_FOUND_ERROR, null, null);
		 }
		 return t;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> getMultipleServices(Class<T> clazz, 
			DefaultListableBeanFactory beanFactory) throws MintException {
		// process spring beans
		Map<Class<? extends T>, T> collectedMap = Maps.newHashMap();
		if (beanFactory != null) {
			try {
				Map<String, T> beanMap = beanFactory.getBeansOfType(clazz);
				if (beanMap != null && beanMap.size() > 0) {
					Set<Entry<String, T>> set = beanMap.entrySet();
					Iterator<Entry<String, T>> itr = set.iterator();
					Entry<String, T> entry = null;
					while (itr.hasNext()) {
						entry = itr.next();
						collectedMap.putIfAbsent((Class<? extends T>) entry.getValue().getClass(), entry.getValue());
					}
				}
			} catch (BeansException e) {
				LOG.info("No implements for {} in SPRING container, skip!", clazz.getName());
			}
		}
		// process SPI
		 ServiceLoader<T> loader = ServiceLoader.load(clazz);
		 Iterator<T> itr = loader.iterator();
		 T t = null;
		 while (itr.hasNext()) {
			 t = itr.next();
			 collectedMap.putIfAbsent((Class<? extends T>) t.getClass(), t);
		 }
		 if (collectedMap.isEmpty()) {
			 throw MintException.getException(Error.IMPLEMENTATION_NOT_FOUND_ERROR, null, null);
		 }
		 return collectedMap.values();
	}
	
}
