package com.mint.service.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.retry.annotation.EnableRetry;

import com.mint.service.annotation.importer.MintServiceImporter;
import com.mint.service.interceptor.MintInterceptor;
import com.mint.service.interceptor.log.CommonLogInterceptor;
import com.mint.service.interceptor.net.PipelineInterceptor;
import com.mint.service.metadata.ServiceMetadataProvider;

/**
 * 本注解仅可应用于mint中台服务
 * 
 * @author ningfanchu
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication(scanBasePackages="com.mint.service")
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableHystrix
@Inherited
@EnableRetry
@Import(MintServiceImporter.class)
@Documented
public @interface MintService {
	// 每个mint service 必须实现ServiceMetaDataProvider接口 并注入spring容器
	Class<? extends ServiceMetadataProvider> metaDataProvider();
	// 本服务调用其他服务超时时间（毫秒）
	int readTimeout() default 3000;
	// 本服务长等待调用超时时间（毫秒）
	int longConnectionReadTimeout() default 180000;
	// 本服务与其他服务建立连接超时时间（毫秒）
	int connectTimeout() default 3000;
	// 本服务是否对全局请求上下文（context）进行校验
	boolean validateContext() default false;
	// 使用哪些拦截器
	Class<? extends MintInterceptor>[] includeInterceptors() default {PipelineInterceptor.class, CommonLogInterceptor.class};
	// 不使用哪些拦截器
	Class<? extends MintInterceptor>[] excludeInterceptors() default {};
	// 取消置顶类的自动装载
	@AliasFor(annotation=SpringBootApplication.class, attribute="exclude")
	Class<?>[] noAutoConfigFor() default {};
	
}
