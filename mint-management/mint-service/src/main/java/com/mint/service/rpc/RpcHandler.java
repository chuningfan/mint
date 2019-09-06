package com.mint.service.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.mint.common.annotation.MethodMapping;
import com.mint.common.annotation.MintRpc;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;

@Component
public class RpcHandler {
	
	private static final Map<Class<?>, Object> PROXIES = Maps.newConcurrentMap();
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RestTemplate longConnectionRestTemplate;
	
	private static final String SPLITTER = "/";
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> apiInterfaceClass) throws MintException {
		Object t = PROXIES.get(apiInterfaceClass);
		if (t != null) {
			return (T) t;
		}
		synchronized (apiInterfaceClass) {
			if ((t = PROXIES.get(apiInterfaceClass)) != null) {
				return (T) t;
			}
			if (apiInterfaceClass.isAnnotationPresent(MintRpc.class) && apiInterfaceClass.isInterface()) {
				MintRpc mr = apiInterfaceClass.getAnnotation(MintRpc.class);
				String protocol = mr.protocolType().getProtocol();
				RestTemplate template = mr.longConnection() ? longConnectionRestTemplate : restTemplate;
				String targetServiceName = mr.serviceName();
				String requestMapping = mr.requestMapping();
				if (!requestMapping.startsWith(SPLITTER)) {
					requestMapping = String.format("/%s", requestMapping);
				}
				StringBuilder builder = new StringBuilder(protocol);
				builder.append(targetServiceName).append(requestMapping).toString();
				String baseUri = builder.toString();
				t = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{apiInterfaceClass}, new InvocationHandler() {
					@SuppressWarnings("rawtypes" )
					@Override
					public Object invoke(Object obj, Method method, Object[] parameters) throws Throwable {
						if (!method.isAnnotationPresent(MethodMapping.class)) {
							throw MintException.getException(Error.INTER_ERROR, null, "No method mapping on method: %s", method.getName());
						}
						MethodMapping mm = method.getAnnotation(MethodMapping.class);
						String mmVal = mm.value();
						if (StringUtils.isBlank(mmVal)) {
							throw MintException.getException(Error.INTER_ERROR, null, "No method mapping on method: %s", method.getName());
						}
						mmVal = mmVal.startsWith(SPLITTER) ? mmVal : (SPLITTER + mmVal);
						String url = baseUri + mmVal; 
						RequestMethod reqMethod = mm.requestMethod();
						Type returnType = method.getGenericReturnType();
						ParameterizedType pType = null;
						ParameterizedTypeReference ptr = null;
						if (returnType instanceof ParameterizedType) {
							pType = (ParameterizedType) returnType;
							ptr = ParameterizedTypeReference.forType(pType);
						}
						switch (reqMethod) {
						case POST:
							int paramSize = parameters.length;
							if (paramSize != 1) {
								throw MintException.getException(Error.INTER_ERROR, null, "POST RPC should have only one parameter (If you have multiple parameters, please wrap them into ONE.)");
							}
							if (ptr != null) {
								HttpEntity<Object> httpEntity = new HttpEntity<>(parameters[0]); 
								return restTemplate.exchange(url, HttpMethod.POST, httpEntity, ptr).getBody();
							} else {
								return template.postForEntity(url, parameters[0], method.getReturnType()).getBody();
							}
						case GET:
							Parameter[] params = method.getParameters();
							Parameter p = null;
							PathVariable pv = null;
							String paramName = "{%s}";
							String reqURL = url;
							for (int i = 0; i < params.length; i ++) {
								p = params[i];
								if (p.isAnnotationPresent(PathVariable.class)) {
									pv = p.getAnnotation(PathVariable.class);
									reqURL = reqURL.replace(String.format(paramName, pv.name()), parameters[i].toString());
								}
							}
							if (ptr != null) {
								return restTemplate.exchange(reqURL, HttpMethod.GET, null, ptr).getBody();
							} else {
								return template.getForEntity(reqURL, method.getReturnType()).getBody();
							}
						default: 
							throw MintException.getException(Error.INTER_ERROR, null, "RPC handler just supports POST and GET");
						}
					}
				});
				PROXIES.put(apiInterfaceClass, t);
				return (T) t;
			} else {
				throw MintException.getException(Error.ILLEGAL_PARAM_ERROR, null, "Invalid mint rpc api");
			}
		}
	}
	
}
