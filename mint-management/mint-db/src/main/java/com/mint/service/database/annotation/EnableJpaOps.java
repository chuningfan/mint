package com.mint.service.database.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.config.DefaultRepositoryBaseClass;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableJpaAuditing(auditorAwareRef="mintAuditorAware")
@EnableJpaRepositories
@EntityScan
@Documented
public @interface EnableJpaOps {
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "value")
	String[] value() default {};
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "basePackages")
	String[] basePackages() default {};
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "basePackageClasses")
	Class<?>[] basePackageClasses() default {};
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "includeFilters")
	Filter[] includeFilters() default {};
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "excludeFilters")
	Filter[] excludeFilters() default {};
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "repositoryImplementationPostfix")
	String repositoryImplementationPostfix() default "Impl";
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "namedQueriesLocation")
	String namedQueriesLocation() default "";
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "queryLookupStrategy")
	Key queryLookupStrategy() default Key.CREATE_IF_NOT_FOUND;
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "repositoryFactoryBeanClass")
	Class<?> repositoryFactoryBeanClass() default JpaRepositoryFactoryBean.class;
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "repositoryBaseClass")
	Class<?> repositoryBaseClass() default DefaultRepositoryBaseClass.class;
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "entityManagerFactoryRef")
	String entityManagerFactoryRef() default "entityManagerFactory";
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "transactionManagerRef")
	String transactionManagerRef() default "transactionManager";
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "considerNestedRepositories")
	boolean considerNestedRepositories() default false;
	@AliasFor(annotation = EnableJpaRepositories.class, attribute = "enableDefaultTransactions")
	boolean enableDefaultTransactions() default true;
	@AliasFor(annotation = EntityScan.class, attribute="basePackages")
	String[] entityScanPackages() default {};
	@AliasFor(annotation = EntityScan.class, attribute="basePackageClasses")
	Class<?>[] entityClasses() default {};
}
