package com.mint.service.email.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.mint.service.email.annotation.importer.MultipleSMTPImporter;
import com.mint.service.email.route.RoutingStrategy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MultipleSMTPImporter.class)
public @interface EmailConfig {
	RoutingStrategy strategy() default RoutingStrategy.HASH;
	String propertiesPath() default "classpath:routing";
	String filePrex() default "email_";
}
