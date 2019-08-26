package com.mint.service.user.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;
import com.mint.common.context.UserContext;
import com.mint.service.user.dao.AccountDao;
import com.mint.service.user.dto.login.LoginFormData;
import com.mint.service.user.dto.login.UpdatePwdData;
import com.mint.service.user.dto.reg.AddressDto;
import com.mint.service.user.dto.reg.BasicInfo;
import com.mint.service.user.dto.reg.CredentialFormData;
import com.mint.service.user.entity.AccountEntity;
import com.mint.service.user.entity.AddressEntity;
import com.mint.service.user.entity.UserEntity;

@RestController
@RequestMapping("/service/u_auth")
public class AuthEndpoint implements AuthOperationService {

	@Autowired
	private AccountDao accountDao;
	
	@PostMapping("/doReg")
	@Transactional
	@Override
	public boolean doReg(@RequestBody CredentialFormData data) {
		String username = data.getUsername();
		String password = data.getPassword();
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setUsername(username);
		accountEntity.setPassword(password);
		accountDao.save(accountEntity);
		return true;
	}

	@PostMapping("/doUpdatePwd")
	@Transactional
	@Override
	public boolean updatePwd(@RequestBody UpdatePwdData data) {
		accountDao.updatePassword(data.getNewPassword(), data.getUsername(), data.getOldPassword());
		return true;
	}

	@PostMapping("/doLogin")
	@Transactional
	@Override
	public UserContext doLogin(@RequestBody LoginFormData data) {
		
		return null;
	}

	@PostMapping("/doSaveInfo")
	@Transactional
	@Override
	public boolean saveOrUpdateBasicInfo(@RequestBody BasicInfo info) {
		AccountEntity accountEntity = accountDao.getOne(info.getAccountId());
		UserEntity user = accountEntity.getUser();
		if (user == null) {
			user = new UserEntity();
		}
		if (info.getAddresses() != null && !info.getAddresses().isEmpty()) {
			Set<AddressEntity> set = Sets.newHashSet();
			for(AddressDto addr: info.getAddresses()) {
				set.add(AddressEntity.createEntity(addr));
			}
			if (user.getAddress() != null) {
				user.getAddress().clear();
				user.getAddress().addAll(set);
			}
		} 
		user.setAvatar(info.getAvatar());
		user.setEmail(info.getEmail());
		user.setFamilyName(info.getFamilyName());
		user.setGender(info.getGender());
		user.setGivenName(info.getGivenName());
		user.setIdNumber(info.getIdNumber());
		user.setMobile(info.getMobile());
		accountDao.save(accountEntity);
		return false;
	}

	@GetMapping("/doTest/{param1}/{param2}")
	@Override
	public boolean doTest(@PathVariable(name="param1") String param1, @PathVariable(name="param2") String param2) {
		System.out.println(param1);
		System.out.println(param2);
		return true;
	}
	
}
