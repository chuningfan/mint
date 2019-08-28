package com.mint.service.env;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

public class SpringEnvironmentManager extends StandardEnvironment {

	@Override
	protected void customizePropertySources(MutablePropertySources propertySources) {
		try {
			Properties pro = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application-actuator.properties"));
			PropertiesPropertySource pps = new PropertiesPropertySource("MintServiceActuator", pro);
			propertySources.addLast(pps);
			super.customizePropertySources(propertySources);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	
}
