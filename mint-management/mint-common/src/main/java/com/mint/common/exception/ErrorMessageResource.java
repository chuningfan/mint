package com.mint.common.exception;

import java.util.Map;

import com.google.common.collect.Maps;

public class ErrorMessageResource {

	private static final Map<Integer, Map<Lang, String>> resourceMap = Maps.newHashMap();
	
	private static void putData(Integer errorCode, Lang lang, String message) {
		Map<Lang, String> map = resourceMap.get(errorCode);
		if (map == null) {
			map = Maps.newHashMap();
		}
		map.put(lang, message);
		resourceMap.put(errorCode, map);
	}
	
	private static String getErrorMessageByCondition(Integer errorCode, Lang lang) {
		return ErrorMessageProviderFactory.get(lang).getMessage(errorCode);
	}
	
	static {
		int errorCode = 0;
		for (Error e: Error.values()) {
			errorCode = e.getCode();
			String msg = null;
			for (Lang lang: Lang.values()) {
				msg = getErrorMessageByCondition(errorCode, lang);
				putData(errorCode, lang, msg);
			}
		}
	}
	
	public static String getMessage(Integer errorCode, Lang lang) {
		return resourceMap.get(errorCode) == null ? null : resourceMap.get(errorCode).get(lang);
	}
	
}
