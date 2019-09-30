package com.mint.service.app;

import com.mint.common.constant.MintServiceStarter;
import com.mint.service.annotation.MintService;
import com.mint.service.app.meta.AppServiceMetadataProvider;

@MintService(metadataProvider = AppServiceMetadataProvider.class)
public class AppServiceStarter {
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		MintServiceStarter.start(args);
	}
	
}
