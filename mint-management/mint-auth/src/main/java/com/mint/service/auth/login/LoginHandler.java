package com.mint.service.auth.login;

import java.util.Date;
import java.util.List;
import java.util.Observable;

import com.google.common.base.Joiner;
import com.mint.common.context.UserContext;
import com.mint.common.enums.LoginResult;
import com.mint.common.enums.LoginType;
import com.mint.common.enums.UserStatus;
import com.mint.service.auth.exception.AuthException;
import com.mint.service.auth.listener.LoginListener;
import com.mint.service.context.ServiceContext;

public abstract class LoginHandler<FD> extends Observable {
	
	protected final LoginListener loginListener;
	
	protected LoginHandler() {
		loginListener = ServiceContext.beanFactory.getBean(LoginListener.class);
		addObserver(loginListener);
	}
	
	public void doLogin(FD formData) throws AuthException {
		UserContext context = login(formData);
		if (context != null && context.getStatus() != null) {
			if (UserStatus.INACTIVE == context.getStatus()) {
				throw new AuthException(LoginResult.INACTIVE);
			} else if (UserStatus.LOCKED == context.getStatus()) {
				throw new AuthException(LoginResult.LOCKED);
			} else {
				context.setLoginType(LoginType.NORMAL);
				String desc = "User type: %n; ID: %n; role IDs: %s; logged in from market: %n";
				List<Long> roleIds = context.getRoleIds();
				String roles = Joiner.on(",").join(roleIds);
				context.setDescription(String.format(desc, context.getUserTypeId(), context.getUserId(), roles, context.getMarketId()));
				context.setPrevLoginTime(new Date());
				setChanged();
				notifyObservers(context);
			}
		}
		throw new AuthException(LoginResult.NOACCOUNT);
	}
	
	protected abstract UserContext login(FD formData);
	
}
