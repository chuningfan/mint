package com.mint.common.utils;

import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil {
	
	public static String getIpAddress(HttpServletRequest request) {  
		String ip = request.getHeader("x-forwarded-for");  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getRemoteAddr();  
		}  
		return ip;  
	}
	
	public static String getCookieValue(HttpServletRequest req, String key) {
		if (req.getCookies() == null || req.getCookies().length == 0) {
			return null;
		}
		Optional<Cookie> opt = Stream.of(req.getCookies()).filter(c -> c.getName().equals(key)).findFirst();
		if (opt.isPresent()) {
			return opt.get().getValue();
		}
		return null;
	}
	
	public static void deleteCookiesByDomainName(HttpServletRequest req, HttpServletResponse resp, String domainName) {
		if (req.getCookies() == null || req.getCookies().length == 0) {
			return;
		}
		if (Stream.of(req.getCookies()).anyMatch(c -> c.getDomain().equals(domainName))) {
			Stream.of(req.getCookies()).filter(c -> c.getDomain().equals(domainName)).forEach(c -> {
				c.setMaxAge(0);
				resp.addCookie(c);
			});
		}
	}
	
	public static void deleteCookiesByKey(HttpServletRequest req, HttpServletResponse resp, String key) {
		if (Stream.of(req.getCookies()).anyMatch(c -> c.getName().equals(key))) {
			Stream.of(req.getCookies()).filter(c -> c.getName().equals(key)).forEach(c -> {
				c.setMaxAge(0);
				resp.addCookie(c);
			});
		}
	}
	
}
