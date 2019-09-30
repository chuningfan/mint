package com.mint.common.constant;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.boot.SpringApplication;

public class MintServiceStarter {
	
	public static final void start(String[] args) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		try {
			Class<?> clazz = loader.loadClass("org.springframework.boot.SpringBootBanner");
			Field f = clazz.getDeclaredField("BANNER");
			Field modifiers = f.getClass().getDeclaredField("modifiers");
	        modifiers.setAccessible(true);
	        modifiers.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			f.setAccessible(true);
			f.set(clazz, getMintBanner());
		} catch (Exception e) {
		}
		try {
			SpringApplication.run(loader.loadClass(elements[elements.length - 1].getClassName()), args);
		} catch (ClassNotFoundException e) {
		}
	}
	
	private static String[] getMintBanner() {
		return new String[]{"\n"
				+ "__      __ \n"
				+ "||\\    /|| \n"
				+ "|| \\  / || \n"
				+ "||  \\/  || \n"
				+ "--      -- \n",
				  "    —— \n"
				+ "    || \n"
				+ "    || \n"
				+ "    || \n"
				+ "    -- \n",
				  "__     __ \n"
				+ "||\\    || \n"
				+ "||  \\  || \n"
				+ "||    \\|| \n"
				+ "--     -- \n",
				  "__________ \n"
				+ "---------- \n"
				+ "    || \n"
				+ "    || \n"
				+ "    || \n"
				+ "    -- \n"
				};
	}
	
}
