package com.mint.service.security.context;

import com.mint.common.context.UserContext;

public interface UserContextOperator {
	
	UserContext getContext(String userIdStr);
	
}
