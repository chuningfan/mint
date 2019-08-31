package com.mint.service.rpc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.mint.common.context.TokenResolver;
import com.mint.common.context.TokenThreadLocal;
import com.mint.service.context.ServiceContext;

/**
 * 默认RPC调用client提供
 * 
 * @author ningfanchu 
 *
 */
@Configuration
public class RestTemplateProvider {
	
	@Autowired
	private TokenResolver tokenResolver;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
				String token = TokenThreadLocal.get();
				if (StringUtils.isNotBlank(token)) {
					tokenResolver.setTokenToRequestHeader(token, req);
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
        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        messageConverters.add(jsonHttpMessageConverter);
        List<MediaType> mediaTypes = Lists.newArrayList();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.TEXT_HTML);
        jsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
        jsonHttpMessageConverter.setObjectMapper(objectMapper);
        messageConverters.add(jsonHttpMessageConverter);
        return messageConverters;
	}
	
}
