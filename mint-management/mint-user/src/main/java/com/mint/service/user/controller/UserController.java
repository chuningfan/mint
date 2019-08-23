package com.mint.service.user.controller;

import java.util.Date;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;
import com.mint.common.enums.GenderType;
import com.mint.common.enums.UserStatus;
import com.mint.service.user.entity.AddressEntity;
import com.mint.service.user.entity.UserEntity;
import com.mint.service.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("register")
	public void  registerUser(UserEntity entity) {
		UserEntity userEntity =new UserEntity();
		userEntity.setName("mint");
		userEntity.setAvatar("url");
		userEntity.setEmail("test@mint.com");
		userEntity.setLastLoginTime(new Date());
		userEntity.setGender(GenderType.MALE);
		userEntity.setMobile("13669282222");
		userEntity.setLoginName("mint");
		userEntity.setStatus(UserStatus.ACTIVE);
		Set<AddressEntity> addressList = Sets.newHashSet();
		AddressEntity addressEntity1 = new AddressEntity();
		addressEntity1.setName("address1");
		addressList.add(addressEntity1);
		
		AddressEntity addressEntity2 = new AddressEntity();
		addressEntity2.setName("address2");
		addressList.add(addressEntity2);
		
		userEntity.setAddress(addressList);
		userService.saveOrUpdateUser(userEntity);
	}
	
	@GetMapping("find/{id}")
	public  void findUserById(@PathParam(value = "id") Long id) {
		userService.findUserById(id);
	}

}
