package com.mint.service.user.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Sets;
import com.mint.common.context.UserContext;
import com.mint.common.dto.web.WebResponse;
import com.mint.common.exception.Error;
import com.mint.common.exception.MintException;
import com.mint.service.security.sault.DigestsUtil;
import com.mint.service.user.dao.AccountDao;
import com.mint.service.user.dto.login.LoginFormData;
import com.mint.service.user.dto.login.UpdatePwdData;
import com.mint.service.user.dto.reg.AddressDto;
import com.mint.service.user.dto.reg.BasicInfo;
import com.mint.service.user.dto.reg.CredentialFormData;
import com.mint.service.user.entity.AccountEntity;
import com.mint.service.user.entity.AddressEntity;
import com.mint.service.user.entity.RoleEntity;
import com.mint.service.user.entity.UserEntity;

@RestController
@RequestMapping("/u_auth")
public class AuthEndpoint implements AuthOperationService {

	@Autowired
	private AccountDao accountDao;
	
	@PostMapping("/doReg")
	@Transactional
	@Override
	public WebResponse<Boolean> doReg(@RequestBody CredentialFormData data) {
		if ( 0 < accountDao.findAccountCountByUsername(data.getUsername())) {
			return new WebResponse<Boolean>(MintException.getException(Error.USER_DUPLICATE_ERROR, null, null));
		}
		String username = data.getUsername();
		String password = data.getPassword();
		AccountEntity accountEntity = new AccountEntity();
		accountEntity.setUsername(username);
		accountEntity.setPassword(DigestsUtil.shap(password));
		accountDao.save(accountEntity);
		return new WebResponse<Boolean>(true);
	}

	@PostMapping("/doUpdatePwd")
	@Transactional
	@Override
	public WebResponse<Boolean> updatePwd(@RequestBody UpdatePwdData data) {
		try {
			accountDao.updatePassword(data.getNewPassword(), data.getUsername(), data.getOldPassword());
		} catch (Exception e) {
			return new WebResponse<Boolean>(MintException.getException(Error.INTER_ERROR, null, null));
		}
		return new WebResponse<Boolean>(true);
	}

	@PostMapping("/doLogin")
	@Transactional
	@Override
	public WebResponse<UserContext> doLogin(@RequestBody LoginFormData data) {
		AccountEntity account = accountDao.findByCredential(data.getUsername(), DigestsUtil.shap(data.getPassword()));
		if (account == null) {
			return new WebResponse<UserContext>(MintException.getException(Error.NO_DATA_FOUND_ERROR, null, null));
		}
		UserContext context = new UserContext();
		context.setAccountId(account.getId());
		context.setBusinessId(account.getBusinessId());
		if (CollectionUtils.isNotEmpty(account.getRoles())) {
			context.setRoleIds(account.getRoles().stream()
					.map(RoleEntity::getId).collect(Collectors.toSet()));
		}
		if (account.getUser() != null) {
			context.setFamilyName(account.getUser().getFamilyName());
			context.setGivenName(account.getUser().getGivenName());
		}
		return new WebResponse<UserContext>(context);
	}

	@PostMapping("/doSaveInfo")
	@Transactional
	@Override
	public WebResponse<Boolean> saveOrUpdateBasicInfo(@RequestBody BasicInfo info) {
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
		return new WebResponse<Boolean>(true);
	}

//	@GetMapping("/doTest/{param1}/{param2}")
//	@Override
//	public boolean doTest(@PathVariable(name="param1") String param1, @PathVariable(name="param2") String param2) {
//		System.out.println(param1);
//		System.out.println(param2);
//		return true;
//	}
	
}
