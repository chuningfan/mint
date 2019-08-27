package com.mint.service.auth.core.ext.normal;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.ContextWrapper;
import com.mint.common.context.UserContext;
import com.mint.common.enums.LoginType;
import com.mint.common.utils.CommonServiceLoader;
import com.mint.service.auth.core.AuthHandler;
import com.mint.service.auth.exception.AuthException;
import com.mint.service.auth.utils.CookieTool;
import com.mint.service.cache.support.redis.RedisHelper;
import com.mint.service.context.ServiceContext;
import com.mint.service.rpc.RpcHandler;
import com.mint.service.user.dto.login.LoginFormData;
import com.mint.service.user.dto.reg.CredentialFormData;
import com.mint.service.user.service.AuthOperationService;

@Component
public class NormalAuthHandler extends AuthHandler {

	@Autowired
	private CookieTool cookieTool;
	
	@Autowired
	private RpcHandler rpcHandler;
	
	@Autowired
	private RedisHelper redisHelper;
	
	private final ContextWrapper contextWrapper;
	
	public NormalAuthHandler() {
		contextWrapper = CommonServiceLoader.getSingleService(ContextWrapper.class, ServiceContext.beanFactory);
	}
	
	@Value("${auth.redis.expire.timeSc}")
	private String expireSc;
	
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
	protected boolean doLogin(Object... data) throws AuthException {
		HttpServletRequest req = (HttpServletRequest) data[0];
		UserContext context;
		try {
			context = contextWrapper.getFromReq(req);
			if (context != null) {
				return true;
			}
		} catch (Exception e1) {
		}
		HttpServletResponse resp = (HttpServletResponse) data[1];
		String username = data[2].toString();
		String password = data[3].toString();
		AuthOperationService aos = rpcHandler.get(AuthOperationService.class);
		LoginFormData formData = new LoginFormData();
		formData.setUsername(username);
		formData.setPassword(password);
		formData.setLoginType(LoginType.NORMAL);
		context = aos.doLogin(formData);
		if (context == null) {
			return false;
		}
		try {
			String token = cookieTool.newCookie(resp, UserContextKeys.USER_CONTEXT, context.getAccountId().toString());
			context.setToken(token);
			context.setPrevLoginTime(System.currentTimeMillis());
			Long expireSeconds = Long.valueOf(expireSc);
			context.setExpirationPeriodMs(expireSeconds * 1000);
			redisHelper.store(context.getAccountId().toString(), context, expireSeconds, TimeUnit.SECONDS);
			return true;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return false;
		}
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
