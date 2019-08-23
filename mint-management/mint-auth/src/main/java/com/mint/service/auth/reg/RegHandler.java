package com.mint.service.auth.reg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.mint.common.dto.reg.NormalRegDto;

@Component
public class RegHandler {

	@Autowired
	private RestTemplate restTemplate;
	
	public void doNReg(NormalRegDto normalRegDto) throws RestClientException {
		restTemplate.postForEntity("http://user-service/service/reg", normalRegDto, Boolean.class);
	}
	
}
