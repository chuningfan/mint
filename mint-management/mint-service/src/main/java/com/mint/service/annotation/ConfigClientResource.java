package com.mint.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PropertySource(value = "")
public @interface ConfigClientResource {

	@AliasFor(annotation = PropertySource.class, attribute = "value")
	String[] classpathConfigResources();
	@AliasFor(annotation = PropertySource.class, attribute = "encoding")
	String encoding() default "UTF-8";
	@AliasFor(annotation = PropertySource.class, attribute = "ignoreResourceNotFound")
	boolean IgnoreResourceIfNotExists() default false;
	
}
