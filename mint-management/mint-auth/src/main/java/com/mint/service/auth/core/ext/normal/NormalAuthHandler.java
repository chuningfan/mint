package com.mint.service.auth.core.ext.normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mint.common.enums.LoginType;
import com.mint.service.auth.core.AuthHandler;
import com.mint.service.rpc.RpcHandler;
import com.mint.service.user.dto.reg.CredentialFormData;
import com.mint.service.user.service.AuthOperationService;

@Component
public class NormalAuthHandler extends AuthHandler {

	@Autowired
	private RpcHandler rpcHandler;
	
	@Override
	protected boolean doReg(Object... data) {
		String username = data[0].toString();
		String password = data[1].toString();
		AuthOperationService aoh = rpcHandler.get(AuthOperationService.class);
		CredentialFormData formData = new CredentialFormData();
		formData.setUsername(username);
		formData.setPassword(password);
		aoh.doReg(formData);
		return true;
	}

	@Override
	protected boolean reg(Object... data) {
		
		return false;
	}

	@Override
	protected boolean doLogin(Object... data) {
		String username = data[0].toString();
		String password = data[1].toString();
		return false;
	}

	@Override
	protected boolean login(Object... data) {
		
		return false;
	}

	@Override
	protected boolean logout(Object... data) {
		
		return false;
	}

	@Override
	protected boolean doUpdatePwd(Object... data) {
		String username = data[0].toString();
		String password = data[1].toString();
		String oldPassword = data[2].toString();
		return false;
	}

	@Override
	protected boolean updatePwd(Object... data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean doGetBackPwd(Object... data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean getBackPwd(Object... data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean checkDuplicateUserName(Object... data) {
		String username = data[0].toString();
		return false;
	}
	
	@Override
	public LoginType getLoginType() {
		return LoginType.NORMAL;
	}

}
