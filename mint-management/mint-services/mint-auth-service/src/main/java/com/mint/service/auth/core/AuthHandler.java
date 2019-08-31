package com.mint.service.auth.core;

import com.mint.common.dto.web.WebResponse;
import com.mint.common.enums.LoginType;
import com.mint.common.exception.MintException;
import com.mint.service.auth.enums.Action;

public abstract class AuthHandler {

	public WebResponse<Boolean> route(Action action, Object...data) throws MintException {
		if (action == null) {
			throw new IllegalArgumentException();
		}
		if (data == null || data.length == 0) {
			throw new IllegalArgumentException();
		}
		switch (action) {
		case DO_LOGIN: 
			return doLogin(data);
		case DO_REG: 
			return doReg(data);
		case DO_UPDATE_PASSWORD: 
			return doUpdatePwd(data);
		case DO_GET_BACK_PASSWORD: 
			return doGetBackPwd(data);
		case LOGIN: 
			return login(data);
		case REG: 
			return reg(data);
		case UPDATE_PASSWORD: 
			return updatePwd(data);
		case GET_BACK_PASSWORD: 
			return getBackPwd(data);
		case LOGOUT: 
			return logout(data);
		default: return new WebResponse<Boolean>(false);
		}
	}
	
	protected abstract WebResponse<Boolean> doReg(Object...data);
	
	protected abstract WebResponse<Boolean> reg(Object...data);
	
	protected abstract WebResponse<Boolean> doLogin(Object...data) throws MintException;
	
	protected abstract WebResponse<Boolean> login(Object...data);
	
	protected abstract WebResponse<Boolean> logout(Object...data);
	
	protected abstract WebResponse<Boolean> doUpdatePwd(Object...data);
	
	protected abstract WebResponse<Boolean> updatePwd(Object...data);
	
	protected abstract WebResponse<Boolean> doGetBackPwd(Object...data);
	
	protected abstract WebResponse<Boolean> getBackPwd(Object...data);
	
	protected abstract WebResponse<Boolean> checkDuplicateUserName(Object...data);

	public abstract LoginType getLoginType();
	
}
