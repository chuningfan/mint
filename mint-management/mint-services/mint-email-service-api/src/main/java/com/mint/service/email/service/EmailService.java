package com.mint.service.email.service;

import java.util.Collection;
import java.util.Set;

import com.mint.common.annotation.MintRpc;
import com.mint.service.email.dto.EmailDto;
import com.mint.service.email.dto.ResultDto;

@MintRpc(requestMapping = "/service", serviceName = "mint-email-service")
public interface EmailService {
	
	Set<ResultDto> sendEmail(Set<EmailDto> emailsToSend);
	
	Set<ResultDto> getEmailResults(Collection<Long> keys);
	
}
