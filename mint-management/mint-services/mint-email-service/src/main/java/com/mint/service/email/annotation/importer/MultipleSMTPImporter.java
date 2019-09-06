package com.mint.service.email.annotation.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ResourceUtils;

import com.google.common.collect.Lists;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.service.email.manager.EmailManager;
import com.mint.service.email.route.RoutingStrategy;

public class MultipleSMTPImporter implements ImportBeanDefinitionRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> attrMap = importingClassMetadata
				.getAnnotationAttributes("com.mint.service.email.annotation.EmailConfig");
		BeanDefinitionBuilder builder = null;
		RoutingStrategy s = (RoutingStrategy) attrMap.get("strategy");
		String resourcePath = (String) attrMap.get("propertiesPath");
		String filePrex = (String) attrMap.get("filePrex");
		builder = BeanDefinitionBuilder.genericBeanDefinition(RoutingContext.class, () -> {
			return new RoutingContext(s);
		});
		registry.registerBeanDefinition("RoutingContext", builder.getRawBeanDefinition());
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
		RoutingContext cxt = beanFactory.getBean(RoutingContext.class);
		File dir = null;
		try {
			dir = ResourceUtils.getFile(resourcePath);
		} catch (FileNotFoundException e) {
		}
		File[] files = dir.listFiles();
		List<File> fileList = Stream.of(files).filter(f -> f.isFile() && f.getName().startsWith(filePrex)).collect(Collectors.toList());
		List<Properties> props = null;
		try {
			props = getProperties(fileList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (CollectionUtils.isNotEmpty(props)) {
			EmailManager mgr = null;
			for (Properties p : props) {
				final String username = p.getProperty("mail.smtp.user");
				final String pwd = p.getProperty("password");
				if (!beanFactory.containsBean(username)) {
					builder = BeanDefinitionBuilder.genericBeanDefinition(EmailManager.class, () -> {
						EmailManager em = new EmailManager(p);
						em.setKey(username);
						em.setPassword(pwd);
						return em;
					});
					registry.registerBeanDefinition(username, builder.getRawBeanDefinition());
					mgr = (EmailManager) beanFactory.getBean(username);
					cxt.addManager(mgr);
				}
			}
		} else {
			throw MintException.getException(Error.RESOURCE_NOT_AVAILABLE_ERROR, null, null);
		}
	}

	private List<Properties> getProperties(List<File> propFiles) throws IOException {
		List<Properties> list = Lists.newArrayList();
		Properties p = null;
		FileInputStream fis = null;
		for (File f: propFiles) {
			try {
				fis = new FileInputStream(f);
				p = new Properties();
				p.load(fis);
				list.add(p);
			} finally {
				if (fis != null) 
					fis.close();
			}
		}
		return list;
	}
	
}
