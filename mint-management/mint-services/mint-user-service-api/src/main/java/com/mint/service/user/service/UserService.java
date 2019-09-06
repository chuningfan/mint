package com.mint.service.user.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mint.common.annotation.MethodMapping;
import com.mint.common.annotation.MintRpc;
import com.mint.common.dto.web.WebResponse;
import com.mint.service.user.dto.user.UserInfo;

@MintRpc(requestMapping = "/service", serviceName = "mint-user-service")
public interface UserService {

	@MethodMapping(value = "/user/{userId}", requestMethod = RequestMethod.GET)
	WebResponse<UserInfo> getUser(@PathVariable(name="userId")Long userId);
	
}
