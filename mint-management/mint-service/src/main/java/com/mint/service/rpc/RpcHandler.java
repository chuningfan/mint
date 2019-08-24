package com.mint.service.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Maps;
import com.mint.common.annotation.MethodMapping;
import com.mint.common.annotation.MintRpc;
import com.mint.service.exception.MintServiceException;

@Component
public class RpcHandler {
	
	private static final Map<Class<?>, Object> PROXIES = Maps.newConcurrentMap();
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RestTemplate longConnectionRestTemplate;
	
	private static final String SPLITTER = "/";
	
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> apiInterfaceClass) throws MintServiceException {
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
				t = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{apiInterfaceClass}, new InvocationHandler() {
					@Override
					public Object invoke(Object obj, Method method, Object[] parameters) throws Throwable {
						if (!method.isAnnotationPresent(MethodMapping.class)) {
							throw new MintServiceException(String.format("No method mapping on method: %s", method.getName()));
						}
						MethodMapping mm = method.getAnnotation(MethodMapping.class);
						String mmVal = mm.value();
						if (StringUtils.isBlank(mmVal)) {
							throw new MintServiceException(String.format("No method mapping on method: %s", method.getName()));
						}
						Class<?> returnType = method.getReturnType();
						mmVal = mmVal.startsWith(SPLITTER) ? mmVal : (SPLITTER + mmVal);
						String url = builder.append(mmVal).toString(); 
						RequestMethod reqMethod = mm.requestMethod();
						switch (reqMethod) {
						case DELETE: 
							template.delete(url, parameters);
							break;
						case HEAD: 
							template.headForHeaders(url, parameters);
							break;
						case OPTIONS: 
							template.optionsForAllow(url, parameters);
							break;
						case PATCH: 
							break;
						case POST: 
							return template.postForEntity(url, null, returnType, parameters);
						case PUT: template.put(url, null, parameters);
							break;
						case TRACE: 
							break;
						default: // get
							return template.getForEntity(url, returnType, parameters);
						}
						return null;
					}
				});
				PROXIES.put(apiInterfaceClass, t);
				return (T) t;
			} else {
				throw new MintServiceException("Invalid mint rpc api.");
			}
		}
	}
	
}
