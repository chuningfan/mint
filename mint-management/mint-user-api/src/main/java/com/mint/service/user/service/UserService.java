package com.mint.service.user.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mint.common.annotation.MethodMapping;
import com.mint.common.annotation.MintRpc;
import com.mint.service.user.dto.user.UserInfo;

@MintRpc(requestMapping = "/service/user", serviceName = "mint-user")
public interface UserService {

	@MethodMapping(value = "/getUserId/{userId}", requestMethod = RequestMethod.GET)
	UserInfo getUser(@PathVariable(name="userId")Long userId);
	
}
