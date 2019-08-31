package com.mint.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Permission;

public class SystemUtil {

	public static int processorCount() {
		return Runtime.getRuntime().availableProcessors();
	}
	
	public static String getLocalAddress() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	public static ClassLoader getCurrentClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}
	
	public static void checkSecurityPermisson(Permission perm) {
		SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			return;
		}
		sm.checkPermission(perm);
	}
	
}
