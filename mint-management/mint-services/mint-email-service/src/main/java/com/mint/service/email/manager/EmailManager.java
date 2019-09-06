package com.mint.service.email.manager;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.mint.service.email.dto.AttachmentDto;
import com.mint.service.email.dto.EmailDto;
import com.mint.service.email.dto.ResourceDto;

@Component
public class EmailManager {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendSimpleMail(EmailDto email) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email.getFromAddress());
        message.setTo(email.getRecipients());
        message.setSubject(email.getSubject());
        message.setText(email.getMessageBody());
        message.setCc(email.getCcList());
        message.setBcc(email.getBccList());
        mailSender.send(message);
	}
	
	public void sendHtmlMail(EmailDto email) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(email.getFromAddress());
        helper.setTo(email.getRecipients());
        helper.setSubject(email.getSubject());
        helper.setText(email.getMessageBody(), true);
        helper.setBcc(email.getBccList());
        helper.setCc(email.getCcList());
        mailSender.send(message);
	}
	
	public void sendAttachmentsMail(EmailDto email) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(email.getFromAddress());
        helper.setTo(email.getRecipients());
        helper.setSubject(email.getSubject());
        helper.setText(email.getMessageBody(), true);
        helper.setCc(email.getCcList());
        helper.setBcc(email.getBccList());
        addAttachments(helper, email.getAttachments());
        mailSender.send(message);
	}
	
	private void addAttachments(MimeMessageHelper helper, List<AttachmentDto> attachments) throws MessagingException {
		if (CollectionUtils.isNotEmpty(attachments)) {
			String fileName = null;
			FileSystemResource file = null;
			for (AttachmentDto a: attachments) {
				fileName = a.getFilePath().substring(a.getFilePath().lastIndexOf("/") + 1);
				file = new FileSystemResource(new File(a.getFilePath()));
				helper.addAttachment(fileName, file);
			}
		}
	}
	
    public void sendResourceMail(EmailDto email) throws MessagingException {
    	MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(email.getFromAddress());
        helper.setTo(email.getRecipients());
        helper.setSubject(email.getSubject());
        helper.setText(email.getMessageBody(), true);
        helper.setCc(email.getCcList());
        helper.setBcc(email.getBccList());
        addResource(helper, email.getResourceDtos());
        mailSender.send(message);
    }
    
    private void addResource(MimeMessageHelper helper, List<ResourceDto> resourceDtos) throws MessagingException {
    	if (CollectionUtils.isNotEmpty(resourceDtos)) {
    		FileSystemResource res = null;
    		for (ResourceDto r: resourceDtos) {
    			res = new FileSystemResource(new File(r.getResourcePath()));
    			helper.addInline((String) r.getResourceId(), res);
    		}
    	}
    }
	
}
