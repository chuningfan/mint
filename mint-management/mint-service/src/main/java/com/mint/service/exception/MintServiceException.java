package com.mint.service.exception;

/**
 * 中台服务公共异常，具体服务可拓展
 * 
 * @author ningfanchu
 *
 */
public class MintServiceException extends RuntimeException {

	private static final long serialVersionUID = 5436858030219924312L;

	public MintServiceException(String errorMessage) {
		super(errorMessage);
	}
	
}
