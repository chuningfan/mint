package com.mint.service.database.exception;

public class AuditingException extends RuntimeException{
	
	private static final long serialVersionUID = -4996792656543335783L;

	public AuditingException() {
		
	}
	
	public AuditingException(String message) {
		super(message);
	}
	
}
