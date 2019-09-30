package com.mint.service.map.starter;

import com.mint.common.constant.MintServiceStarter;
import com.mint.service.annotation.MintService;
import com.mint.service.map.metadata.MapServiceMetadataProvider;

@MintService(metadataProvider = MapServiceMetadataProvider.class)
public class MintMapServiceStarter {

	public static void main(String[] args) {
		MintServiceStarter.start(args);
	}
	
}
