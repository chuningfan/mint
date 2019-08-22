package com.mint.service.auth.logout;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.utils.HttpUtil;
import com.mint.service.auth.listener.LoginListener;
import com.mint.service.auth.listener.LogoutListener;
import com.mint.service.auth.listener.dto.NormalLogoutDto;
import com.mint.service.context.ServiceContext;
import com.mint.service.security.context.ContextCookieUtil;

public abstract class LogoutHandler<T> extends Observable {
	
protected final LogoutListener logoutListener;
	
	protected LogoutHandler() {
		logoutListener = ServiceContext.beanFactory.getBean(LogoutListener.class);
		addObserver(logoutListener);
	}
	
	public void logout(T data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (data instanceof NormalLogoutDto) {
			NormalLogoutDto dto = (NormalLogoutDto) data;
			String cookieValue = HttpUtil.getCookieValue(dto.getRequest(), UserContextKeys.USER_CONTEXT);
			String userIdStr = ContextCookieUtil.getCookieRealValue(cookieValue);
			doLogout(data);
			HttpUtil.deleteCookiesByKey(dto.getRequest(), dto.getResponse(), UserContextKeys.USER_CONTEXT);
			setChanged();
			notifyObservers(userIdStr);
		}
	}
	
	protected abstract void doLogout(T data);
	
}
