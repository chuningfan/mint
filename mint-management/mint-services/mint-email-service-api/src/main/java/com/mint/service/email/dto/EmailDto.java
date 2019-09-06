package com.mint.service.email.dto;

import java.util.List;

public class EmailDto {
	
	private String subject;
	
	private String[] recipients;
	
	private String[] ccList;
	
	private String[] bccList;
	
	private String messageBody;
	
	private String fromAddress;
	
	private List<AttachmentDto> attachments;
	
	private List<ResourceDto> resourceDtos;

	private long delaySendMs = 0;
	
	private Long key;
	
	private Long fromAccountId;
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public String[] getCcList() {
		return ccList;
	}

	public void setCcList(String[] ccList) {
		this.ccList = ccList;
	}

	public String[] getBccList() {
		return bccList;
	}

	public void setBccList(String[] bccList) {
		this.bccList = bccList;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public List<AttachmentDto> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AttachmentDto> attachments) {
		this.attachments = attachments;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public long getDelaySendMs() {
		return delaySendMs;
	}

	public void setDelaySendMs(long delaySendMs) {
		this.delaySendMs = delaySendMs;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public List<ResourceDto> getResourceDtos() {
		return resourceDtos;
	}

	public void setResourceDtos(List<ResourceDto> resourceDtos) {
		this.resourceDtos = resourceDtos;
	}
	
}
