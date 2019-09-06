package com.mint.service.email.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.service.email.annotation.importer.RoutingContext;
import com.mint.service.email.dto.EmailDto;
import com.mint.service.email.dto.ResultDto;

@RestController
@RequestMapping("/test")
public class EmailEndpoint implements EmailService {

	@Autowired
	private RoutingContext routingContext;
	
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
		email.setSubject("TEST");
		email.setRecipients(new String[]{"297231034@qq.com"});
		email.setFromAddress("mintmail@163.com");
		email.setMessageBody("你好，\n	这是一封测试邮件，请勿回复。\n 感谢配合！");
		routingContext.getManager().sendSimpleMail(email);
	}

}
