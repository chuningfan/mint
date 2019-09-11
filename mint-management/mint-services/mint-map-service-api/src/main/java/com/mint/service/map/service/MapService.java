package com.mint.service.map.service;

import com.mint.service.map.dto.MapQueryRequest;

public interface MapService {
	
	<T> T query(MapQueryRequest req) throws Exception;
	
	void saveData(MapQueryRequest req);
	
}
