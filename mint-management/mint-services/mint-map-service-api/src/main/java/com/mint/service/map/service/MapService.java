package com.mint.service.map.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMethod;

import com.mint.common.annotation.MethodMapping;
import com.mint.common.annotation.MintRpc;
import com.mint.service.map.dto.AgencyInfo;
import com.mint.service.map.dto.QueryDto;

@MintRpc(requestMapping = "/map", serviceName = "mint-map-service")
public interface MapService {
	
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/query")
	List<Long> query(QueryDto queryDto) throws Exception;
	
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/saveAgencyData")
	void saveAgencyData(AgencyInfo ai);
	
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/removeAgencyInfo")
	void removeAgencyInfo(AgencyInfo ai);
	
	@MethodMapping(requestMethod = RequestMethod.POST, value = "/updateAgencyInfo")
	void updateAgencyInfo(AgencyInfo ai);
	
}
