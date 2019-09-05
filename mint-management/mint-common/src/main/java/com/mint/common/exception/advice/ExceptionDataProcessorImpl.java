package com.mint.common.exception.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mint.common.exception.MintException;

public class ExceptionDataProcessorImpl implements ExceptionDataProcessor {
	private final static Logger logger = LoggerFactory.getLogger(ExceptionDataProcessorImpl.class);
	@Override
	public void process(MintException e) {
		logger.error("mint error {}",e);
	}

}
