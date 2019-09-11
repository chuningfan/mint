package com.mint.service.map.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.service.map.dto.MapQueryRequest;

@RestController
@RequestMapping("/map")
public class MapEndpoint implements MapService {

	
	@Override
	public <T> T query(MapQueryRequest req) throws Exception {
		return null;
	}

	@Override
	public void saveData(MapQueryRequest req) {
		
	}

}
