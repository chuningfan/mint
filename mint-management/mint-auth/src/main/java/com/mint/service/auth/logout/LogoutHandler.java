package com.mint.service.auth.logout;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;

import javax.servlet.ServletException;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.utils.HttpUtil;
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
	
	public void logout(T data) throws NoSuchAlgorithmException, IOException, ServletException {
		if (data instanceof NormalLogoutDto) {
			NormalLogoutDto dto = (NormalLogoutDto) data;
			String cookieValue = HttpUtil.getCookieValue(dto.getRequest(), UserContextKeys.USER_CONTEXT);
			String userIdStr = ContextCookieUtil.getCookieRealValue(cookieValue);
			HttpUtil.deleteCookiesByKey(dto.getRequest(), dto.getResponse(), UserContextKeys.USER_CONTEXT);
			setChanged();
			notifyObservers(userIdStr);
			doLogout(data);
		}
	}
	
	protected abstract void doLogout(T data) throws IOException, ServletException;
	
}
