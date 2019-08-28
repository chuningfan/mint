package com.mint.service.user.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mint.common.dto.web.WebResponse;
import com.mint.service.user.dto.user.UserInfo;

@RestController
@RequestMapping("/service")
public class UserEndpoint implements UserService {

	@Override
	@GetMapping("/user/{userId}")
	public WebResponse<UserInfo> getUser(@PathVariable(name = "userId")Long userId) {
		UserInfo info = new UserInfo();
		info.setFamilyName("Cai");
		info.setGivenName("Xukun");
		return new WebResponse<UserInfo>(info);
	}

}
