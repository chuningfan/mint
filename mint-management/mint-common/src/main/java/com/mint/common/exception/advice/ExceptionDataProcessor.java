package com.mint.common.exception.advice;

import com.mint.common.exception.MintException;

public interface ExceptionDataProcessor {

	void process(MintException e);
	
}
