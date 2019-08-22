package com.mint.service.exception;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringUtils;


public class Exceptions {
	
	public static MintServiceException get(Class<? extends MintServiceException> exceptionClass, String message, Object...args) {
		if (StringUtils.isNotBlank(message) && args != null && args.length > 0) {
			message = String.format(message, args);
		}
		if (exceptionClass == MintServiceException.class) {
			return new MintServiceException(message);
		} else {
			if (MintServiceException.class.isAssignableFrom(exceptionClass)) {
				try {
					return exceptionClass.getConstructor(String.class).newInstance(message);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					return new MintServiceException(message);
				}
			} else {
				return new MintServiceException(message);
			}
		}
	}
	
}
