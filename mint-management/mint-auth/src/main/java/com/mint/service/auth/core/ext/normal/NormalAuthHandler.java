package com.mint.service.auth.core.ext.normal;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mint.common.constant.UserContextKeys;
import com.mint.common.context.UserContext;
import com.mint.common.enums.LoginType;
import com.mint.service.auth.core.AuthHandler;
import com.mint.service.auth.exception.AuthException;
import com.mint.service.auth.utils.CookieTool;
import com.mint.service.cache.support.redis.RedisHelper;
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
		HttpServletResponse resp = (HttpServletResponse) data[0];
		String username = data[1].toString();
		String password = data[2].toString();
		AuthOperationService aos = rpcHandler.get(AuthOperationService.class);
		LoginFormData formData = new LoginFormData();
		formData.setUsername(username);
		formData.setPassword(password);
		formData.setLoginType(LoginType.NORMAL);
		UserContext context = aos.doLogin(formData);
		if (context == null) {
			return false;
		}
		try {
			String token = cookieTool.newCookie(resp, UserContextKeys.USER_CONTEXT, context.getAccountId().toString());
			context.setToken(token);
			redisHelper.store(context.getAccountId().toString(), context, Long.valueOf(expireSc), TimeUnit.SECONDS);
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
