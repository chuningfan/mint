package com.mint.service.email.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.service.email.dto.EmailDto;
import com.mint.service.email.dto.ResultDto;
import com.mint.service.email.manager.EmailManager;

@RestController
@RequestMapping("/test")
public class EmailEndpoint implements EmailService {

	@Autowired
	private EmailManager emailManager;
	
	@Override
	@PostMapping("/send")
	public Set<ResultDto> sendEmail(Set<EmailDto> emailsToSend) {
		return null;
	}

	@Override
	@GetMapping("/getEmailResults")
	public Set<ResultDto> getEmailResults(Collection<Long> keys) {
		return null;
	}
	
	@GetMapping("/testSend")
	public void testSend() {
		EmailDto email = new EmailDto();
		email.setRecipients(new String[]{"Francis.Fang@activenetwork.com"});
		email.setFromAddress("chuqingning@163.com");
		email.setMessageBody("MINT Test Email, DO NOT REPLY.");
		emailManager.sendSimpleMail(email);
	}

}
