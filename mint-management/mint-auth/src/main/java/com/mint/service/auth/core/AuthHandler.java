package com.mint.service.auth.core;

import com.mint.common.enums.LoginType;
import com.mint.service.auth.enums.Action;

public abstract class AuthHandler {

	public boolean route(Action action, Object...data) {
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
		default: return false;
		}
	}
	
	protected abstract boolean doReg(Object...data);
	
	protected abstract boolean reg(Object...data);
	
	protected abstract boolean doLogin(Object...data);
	
	protected abstract boolean login(Object...data);
	
	protected abstract boolean logout(Object...data);
	
	protected abstract boolean doUpdatePwd(Object...data);
	
	protected abstract boolean updatePwd(Object...data);
	
	protected abstract boolean doGetBackPwd(Object...data);
	
	protected abstract boolean getBackPwd(Object...data);
	
	protected abstract boolean checkDuplicateUserName(Object...data);

	public abstract LoginType getLoginType();
	
}
