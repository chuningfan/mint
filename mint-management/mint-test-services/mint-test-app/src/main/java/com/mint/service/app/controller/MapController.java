package com.mint.service.app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;
import com.mint.service.map.dto.AgencyInfo;
import com.mint.service.map.dto.QueryDto;
import com.mint.service.map.service.MapService;
import com.mint.service.rpc.RpcHandler;

@RestController
@RequestMapping("/maptest")
public class MapController {

	@Autowired
	private RpcHandler rpcHandler;
	
	@GetMapping("/test")
	public void saveAgencyInfo() throws Exception {
		MapService mapService = rpcHandler.get(MapService.class);
		// save ai
		AgencyInfo ai = new AgencyInfo();
		ai.setAgencyId(1L);
		Set<String> keys = Sets.newHashSet();
		keys.add("washwall");
		ai.setKeys(keys);
		ai.setLatitude(12.33);
		ai.setLongitude(33.333);
		mapService.saveAgencyData(ai);
		// update ai
		Set<String> newKeys = Sets.newHashSet();
		newKeys.add("washwall");
		newKeys.add("tz");
		ai.setKeys(newKeys);
		ai.setLatitude(12.22);
		ai.setLongitude(33.2222);
		mapService.updateAgencyInfo(ai);
		
		// delete ai
		ai.getKeys().remove("tz");
		mapService.removeAgencyInfo(ai);
		
		// query
		QueryDto q = new QueryDto();
		Set<String> qKeys = Sets.newHashSet();
		qKeys.add("washwall");
		q.setKeys(qKeys);
		q.setRadius(10);
		q.setLongitude(33);
		q.setLongitude(12);
		mapService.query(q);
	}
	
}
