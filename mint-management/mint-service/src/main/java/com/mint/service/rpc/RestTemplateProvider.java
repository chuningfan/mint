package com.mint.service.rpc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.context.UserContextThreadLocal;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.context.ServiceContext;

/**
 * 默认RPC调用client提供
 * 
 * @author ningfanchu 
 *
 */
@Configuration
public class RestTemplateProvider {
	
	private final ContextWrapper wrapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public RestTemplateProvider() {
		wrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return create(ServiceContext.readTimeout);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate longConnectionRestTemplate() {
		return create(ServiceContext.longConnectionReadTimeout);
	}
	
	private RestTemplate create(int readTimeout) {
		RestTemplate template = new RestTemplate();
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(ServiceContext.connectTimeout);
		requestFactory.setReadTimeout(readTimeout);
		template.setRequestFactory(requestFactory);
		template.setErrorHandler(new DefaultResponseErrorHandler());
		template.setInterceptors(Lists.newArrayList(new ClientHttpRequestInterceptor() {
			@Override
			public ClientHttpResponse intercept(HttpRequest req, byte[] body, ClientHttpRequestExecution exe)
					throws IOException {
				UserContext context = UserContextThreadLocal.get();
				if (context != null) {
					wrapper.setUserContextIntoRequestHeader(context, req);
				}
				return exe.execute(req, body);
			}
		}));
		template.setMessageConverters(setMessageConverters(template));
		return template;
	}
	
	private List<HttpMessageConverter<?>> setMessageConverters(RestTemplate template) {
		List<HttpMessageConverter<?>> messageConverters = Lists.newArrayList();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.QuoteFieldNames, SerializerFeature.DisableCircularReferenceDetect);
        fastConverter.setFastJsonConfig(fastJsonConfig);

        List<MediaType> fastMediaTypes = Lists.newArrayList();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        messageConverters.add(fastConverter);
        return messageConverters;
	}
	
}
