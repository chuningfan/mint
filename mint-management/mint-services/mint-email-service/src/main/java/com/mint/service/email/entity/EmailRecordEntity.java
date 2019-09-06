package com.mint.service.email.entity;
//
//import java.util.List;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//import com.mint.service.database.entity.IdentifiedEntity;
//import com.mint.service.email.dto.AttachmentDto;
//
//@Entity
//@Table(name="email_recods")
//public class EmailRecordEntity extends IdentifiedEntity {
//
//	private String subject;
//	
//	private String recipients;
//	
//	private String ccList;
//	
//	private String messageBody;
//	
//	private String fromAddress;
//	
//	private List<AttachmentEntity> attachments;
//
//	private long delaySendMs = 0;
//	
//	private Long key;
//	
//	private Long fromAccountId;
//
//	public String getSubject() {
//		return subject;
//	}
//
//	public void setSubject(String subject) {
//		this.subject = subject;
//	}
//
//	public String getRecipients() {
//		return recipients;
//	}
//
//	public void setRecipients(String recipients) {
//		this.recipients = recipients;
//	}
//
//	public String getCcList() {
//		return ccList;
//	}
//
//	public void setCcList(String ccList) {
//		this.ccList = ccList;
//	}
//
//	public String getMessageBody() {
//		return messageBody;
//	}
//
//	public void setMessageBody(String messageBody) {
//		this.messageBody = messageBody;
//	}
//
//	public String getFromAddress() {
//		return fromAddress;
//	}
//
//	public void setFromAddress(String fromAddress) {
//		this.fromAddress = fromAddress;
//	}
//
//	public List<AttachmentEntity> getAttachments() {
//		return attachments;
//	}
//
//	public void setAttachments(List<AttachmentEntity> attachments) {
//		this.attachments = attachments;
//	}
//
//	public long getDelaySendMs() {
//		return delaySendMs;
//	}
//
//	public void setDelaySendMs(long delaySendMs) {
//		this.delaySendMs = delaySendMs;
//	}
//
//	public Long getKey() {
//		return key;
//	}
//
//	public void setKey(Long key) {
//		this.key = key;
//	}
//
//	public Long getFromAccountId() {
//		return fromAccountId;
//	}
//
//	public void setFromAccountId(Long fromAccountId) {
//		this.fromAccountId = fromAccountId;
//	}
//	
//}
